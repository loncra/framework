package io.github.loncra.framework.datasource.test;

import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 原生 JDBC 读写分离测试（不依赖 Spring 事务）
 *
 * @author maurice.chen
 */
public class NativeJdbcReadWriteSeparateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(NativeJdbcReadWriteSeparateTest.class);

    private ReadWriteSeparateDataSource dataSource;
    private DataSource masterDataSource;
    private DataSource slaveDataSource1;
    private DataSource slaveDataSource2;

    @BeforeEach
    public void setUp() throws Exception {
        // 创建主数据源
        masterDataSource = createH2DataSource("masterdb");

        // 创建从数据源
        slaveDataSource1 = createH2DataSource("testdb");
        slaveDataSource2 = createH2DataSource("testdb");

        // 初始化主库表结构
        initializeDatabase(masterDataSource);

        // 初始化从库表结构（使用同一个数据库，实际场景中从库会通过主从复制同步数据）
        initializeDatabase(slaveDataSource1);
        initializeDatabase(slaveDataSource2);

        // 创建读写分离数据源
        List<DataSource> slaves = new ArrayList<>();
        slaves.add(slaveDataSource1);
        slaves.add(slaveDataSource2);
        dataSource = new ReadWriteSeparateDataSource(masterDataSource, slaves);
    }

    @AfterEach
    public void tearDown() {
        // 清除 ThreadLocal 状态
        ReadWriteSeparateDataSource.clearReadOnly();
    }

    /**
     * 创建 H2 数据源
     */
    private DataSource createH2DataSource(String dbName) throws Exception {
        Class<?> hikariClass = Class.forName("com.zaxxer.hikari.HikariDataSource");
        DataSource ds = (DataSource) hikariClass.getDeclaredConstructor().newInstance();

        // 设置属性
        setProperty(ds, "jdbcUrl", "jdbc:h2:mem:" + dbName + ";MODE=MYSQL;DB_CLOSE_DELAY=-1");
        setProperty(ds, "username", "sa");
        setProperty(ds, "password", "");
        setProperty(ds, "driverClassName", "org.h2.Driver");

        return ds;
    }

    /**
     * 设置对象属性
     */
    private void setProperty(
            Object obj,
            String propertyName,
            Object value
    ) throws Exception {
        java.beans.PropertyDescriptor descriptor = new java.beans.PropertyDescriptor(propertyName, obj.getClass());
        java.lang.reflect.Method writeMethod = descriptor.getWriteMethod();
        if (writeMethod != null) {
            writeMethod.invoke(obj, value);
        }
    }

    /**
     * 初始化数据库表结构
     */
    private void initializeDatabase(DataSource ds) throws SQLException {
        try (
                Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()
        ) {

            // 创建表
            stmt.execute("DROP TABLE IF EXISTS tb_test_user");
            stmt.execute("CREATE TABLE tb_test_user (" +
                                 "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                                 "username VARCHAR(128) NOT NULL, " +
                                 "email VARCHAR(256), " +
                                 "age INT, " +
                                 "creation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                                 "update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                                 ")");

            // 插入初始数据
            stmt.executeUpdate("INSERT INTO tb_test_user (username, email, age) VALUES ('admin', 'admin@example.com', 30)");
            stmt.executeUpdate("INSERT INTO tb_test_user (username, email, age) VALUES ('user1', 'user1@example.com', 25)");
            stmt.executeUpdate("INSERT INTO tb_test_user (username, email, age) VALUES ('user2', 'user2@example.com', 28)");

            conn.commit();
        }
    }

    /**
     * 测试只读操作路由到从库
     */
    @Test
    public void testReadOnlyOperationRoutesToSlave() throws SQLException {
        LOGGER.info("开始测试只读操作路由到从库...");

        // 设置只读状态
        ReadWriteSeparateDataSource.setReadOnly(true);
        Assertions.assertTrue(ReadWriteSeparateDataSource.getReadOnly(), "应该设置为只读状态");

        // 获取连接（应该路由到从库）
        try (Connection conn = dataSource.getConnection()) {
            Assertions.assertTrue(conn.isReadOnly(), "连接应该是只读的");

            // 执行查询操作
            String sql = "SELECT id, username, email, age FROM tb_test_user WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, 1);
                try (ResultSet rs = pstmt.executeQuery()) {
                    Assertions.assertTrue(rs.next(), "应该查询到数据");
                    Assertions.assertEquals("admin", rs.getString("username"));
                    LOGGER.info("从库查询成功，用户: {}", rs.getString("username"));
                }
            }
        }

        LOGGER.info("只读操作路由到从库测试通过");
    }

    /**
     * 测试写操作路由到主库
     */
    @Test
    public void testWriteOperationRoutesToMaster() throws SQLException {
        LOGGER.info("开始测试写操作路由到主库...");

        // 设置非只读状态（或清除只读状态）
        ReadWriteSeparateDataSource.setReadOnly(false);
        Assertions.assertFalse(ReadWriteSeparateDataSource.getReadOnly(), "应该设置为非只读状态");

        // 获取连接（应该路由到主库）
        try (Connection conn = dataSource.getConnection()) {
            Assertions.assertFalse(conn.isReadOnly(), "连接应该是非只读的");
            conn.setAutoCommit(false);

            // 执行插入操作
            String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "test_user");
                pstmt.setString(2, "test@example.com");
                pstmt.setInt(3, 20);
                int rows = pstmt.executeUpdate();
                Assertions.assertEquals(1, rows, "应该插入1条记录");

                // 获取生成的ID
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    Assertions.assertTrue(rs.next(), "应该获取到生成的ID");
                    Long userId = rs.getLong(1);
                    LOGGER.info("主库插入成功，用户ID: {}", userId);

                    // 在同一个连接中查询刚插入的数据
                    String selectSql = "SELECT username, email, age FROM tb_test_user WHERE id = ?";
                    try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                        selectStmt.setLong(1, userId);
                        try (ResultSet selectRs = selectStmt.executeQuery()) {
                            Assertions.assertTrue(selectRs.next(), "应该查询到刚插入的数据");
                            Assertions.assertEquals("test_user", selectRs.getString("username"));
                            Assertions.assertEquals("test@example.com", selectRs.getString("email"));
                            Assertions.assertEquals(20, selectRs.getInt("age"));
                        }
                    }
                }
            }

            conn.rollback(); // 回滚测试数据
        }

        LOGGER.info("写操作路由到主库测试通过");
    }

    /**
     * 测试通过 Connection.setReadOnly() 动态切换路由
     */
    @Test
    public void testDynamicRoutingByConnectionReadOnly() throws SQLException {
        LOGGER.info("开始测试通过 Connection.setReadOnly() 动态切换路由...");

        // 初始状态：未设置只读，应该使用主库
        try (Connection conn = dataSource.getConnection()) {
            Assertions.assertFalse(conn.isReadOnly(), "初始状态应该是非只读");

            // 设置为只读，应该切换到从库
            conn.setReadOnly(true);
            Assertions.assertTrue(conn.isReadOnly(), "应该设置为只读");
            Assertions.assertTrue(ReadWriteSeparateDataSource.getReadOnly(), "ThreadLocal 应该更新为只读");

            // 执行查询操作（应该从从库读取）
            String selectSql = "SELECT COUNT(*) as total FROM tb_test_user";
            try (
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(selectSql)
            ) {
                Assertions.assertTrue(rs.next(), "应该查询到数据");
                int total = rs.getInt("total");
                Assertions.assertTrue(total >= 3, "应该至少有3条初始数据");
                LOGGER.info("从库查询成功，总数: {}", total);
            }

            // 设置为非只读，应该切换回主库
            conn.setReadOnly(false);
            Assertions.assertFalse(conn.isReadOnly(), "应该设置为非只读");
            Assertions.assertFalse(ReadWriteSeparateDataSource.getReadOnly(), "ThreadLocal 应该更新为非只读");

            // 执行插入操作（应该写入主库）
            conn.setAutoCommit(false);
            String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, "dynamic_user");
                pstmt.setString(2, "dynamic@example.com");
                pstmt.setInt(3, 30);
                int rows = pstmt.executeUpdate();
                Assertions.assertEquals(1, rows, "应该插入1条记录");
                LOGGER.info("主库插入成功");
            }

            conn.rollback(); // 回滚测试数据
        }

        LOGGER.info("通过 Connection.setReadOnly() 动态切换路由测试通过");
    }

    /**
     * 测试原生 JDBC 的 SELECT 操作
     */
    @Test
    public void testNativeJdbcSelect() throws SQLException {
        LOGGER.info("开始测试原生 JDBC SELECT 操作...");

        // 设置为只读，路由到从库
        ReadWriteSeparateDataSource.setReadOnly(true);

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, username, email, age FROM tb_test_user ORDER BY id";
            try (
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)
            ) {

                List<String> usernames = new ArrayList<>();
                while (rs.next()) {
                    usernames.add(rs.getString("username"));
                    LOGGER.debug("查询到用户: ID={}, Username={}, Email={}, Age={}",
                                 rs.getLong("id"),
                                 rs.getString("username"),
                                 rs.getString("email"),
                                 rs.getInt("age"));
                }

                Assertions.assertFalse(usernames.isEmpty(), "应该查询到数据");
                Assertions.assertTrue(usernames.contains("admin"), "应该包含 admin 用户");
                LOGGER.info("原生 JDBC SELECT 操作测试通过，查询到 {} 个用户", usernames.size());
            }
        }
    }

    /**
     * 测试原生 JDBC 的 INSERT 操作
     */
    @Test
    public void testNativeJdbcInsert() throws SQLException {
        LOGGER.info("开始测试原生 JDBC INSERT 操作...");

        // 设置为非只读，路由到主库
        ReadWriteSeparateDataSource.setReadOnly(false);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "native_user");
                pstmt.setString(2, "native@example.com");
                pstmt.setInt(3, 25);

                int rows = pstmt.executeUpdate();
                Assertions.assertEquals(1, rows, "应该插入1条记录");

                // 获取生成的ID
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    Assertions.assertTrue(rs.next(), "应该获取到生成的ID");
                    Long userId = rs.getLong(1);
                    Assertions.assertTrue(userId > 0, "用户ID应该大于0");
                    LOGGER.info("原生 JDBC INSERT 操作测试通过，插入用户ID: {}", userId);
                }
            }

            conn.rollback(); // 回滚测试数据
        }
    }

    /**
     * 测试原生 JDBC 的 UPDATE 操作
     */
    @Test
    public void testNativeJdbcUpdate() throws SQLException {
        LOGGER.info("开始测试原生 JDBC UPDATE 操作...");

        // 设置为非只读，路由到主库
        ReadWriteSeparateDataSource.setReadOnly(false);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            // 先插入一条测试数据
            String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
            Long userId;
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "update_test_user");
                pstmt.setString(2, "update_test@example.com");
                pstmt.setInt(3, 20);
                pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    Assertions.assertTrue(rs.next());
                    userId = rs.getLong(1);
                }
            }

            // 执行更新操作
            String updateSql = "UPDATE tb_test_user SET age = ?, email = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, 30);
                pstmt.setString(2, "updated@example.com");
                pstmt.setLong(3, userId);

                int rows = pstmt.executeUpdate();
                Assertions.assertEquals(1, rows, "应该更新1条记录");

                // 验证更新
                String selectSql = "SELECT age, email FROM tb_test_user WHERE id = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                    selectStmt.setLong(1, userId);
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        Assertions.assertTrue(rs.next(), "应该查询到更新后的数据");
                        Assertions.assertEquals(30, rs.getInt("age"), "年龄应该更新为30");
                        Assertions.assertEquals("updated@example.com", rs.getString("email"), "邮箱应该已更新");
                        LOGGER.info("原生 JDBC UPDATE 操作测试通过，更新用户ID: {}", userId);
                    }
                }
            }

            conn.rollback(); // 回滚测试数据
        }
    }

    /**
     * 测试原生 JDBC 的 DELETE 操作
     */
    @Test
    public void testNativeJdbcDelete() throws SQLException {
        LOGGER.info("开始测试原生 JDBC DELETE 操作...");

        // 设置为非只读，路由到主库
        ReadWriteSeparateDataSource.setReadOnly(false);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            // 先插入一条测试数据
            String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
            Long userId;
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "delete_test_user");
                pstmt.setString(2, "delete_test@example.com");
                pstmt.setInt(3, 25);
                pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    Assertions.assertTrue(rs.next());
                    userId = rs.getLong(1);
                }
            }

            // 验证插入成功
            String countBeforeSql = "SELECT COUNT(*) FROM tb_test_user WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(countBeforeSql)) {
                pstmt.setLong(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    Assertions.assertTrue(rs.next());
                    Assertions.assertEquals(1, rs.getInt(1), "删除前应该有1条记录");
                }
            }

            // 执行删除操作
            String deleteSql = "DELETE FROM tb_test_user WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setLong(1, userId);
                int rows = pstmt.executeUpdate();
                Assertions.assertEquals(1, rows, "应该删除1条记录");
            }

            // 验证删除成功
            String countAfterSql = "SELECT COUNT(*) FROM tb_test_user WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(countAfterSql)) {
                pstmt.setLong(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    Assertions.assertTrue(rs.next());
                    Assertions.assertEquals(0, rs.getInt(1), "删除后应该没有记录");
                    LOGGER.info("原生 JDBC DELETE 操作测试通过，删除用户ID: {}", userId);
                }
            }

            conn.rollback(); // 回滚测试数据
        }
    }

    /**
     * 测试读写混合操作
     */
    @Test
    public void testNativeJdbcReadWriteMixed() throws SQLException {
        LOGGER.info("开始测试原生 JDBC 读写混合操作...");

        // 1. 只读查询（从库）
        ReadWriteSeparateDataSource.setReadOnly(true);
        try (Connection readConn = dataSource.getConnection()) {
            String countSql = "SELECT COUNT(*) as total FROM tb_test_user";
            try (
                    Statement stmt = readConn.createStatement();
                    ResultSet rs = stmt.executeQuery(countSql)
            ) {
                Assertions.assertTrue(rs.next());
                int totalBefore = rs.getInt("total");
                LOGGER.info("从库查询，初始总数: {}", totalBefore);
            }
        }

        // 2. 写操作（主库）
        ReadWriteSeparateDataSource.setReadOnly(false);
        try (Connection writeConn = dataSource.getConnection()) {
            writeConn.setAutoCommit(false);

            String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
            Long userId;
            try (PreparedStatement pstmt = writeConn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "mixed_user");
                pstmt.setString(2, "mixed@example.com");
                pstmt.setInt(3, 28);
                pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    Assertions.assertTrue(rs.next());
                    userId = rs.getLong(1);
                }
            }

            // 在主库连接中查询刚插入的数据
            String selectSql = "SELECT username FROM tb_test_user WHERE id = ?";
            try (PreparedStatement pstmt = writeConn.prepareStatement(selectSql)) {
                pstmt.setLong(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    Assertions.assertTrue(rs.next(), "应该能从主库查询到刚插入的数据");
                    Assertions.assertEquals("mixed_user", rs.getString("username"));
                }
            }

            writeConn.rollback(); // 回滚测试数据
        }

        // 3. 再次只读查询（从库）
        ReadWriteSeparateDataSource.setReadOnly(true);
        try (Connection readConn2 = dataSource.getConnection()) {
            String countSql = "SELECT COUNT(*) as total FROM tb_test_user";
            try (
                    Statement stmt = readConn2.createStatement();
                    ResultSet rs = stmt.executeQuery(countSql)
            ) {
                Assertions.assertTrue(rs.next());
                int totalAfter = rs.getInt("total");
                LOGGER.info("从库查询，操作后总数: {}", totalAfter);
            }
        }

        LOGGER.info("原生 JDBC 读写混合操作测试通过");
    }

    /**
     * 测试多线程环境下的读写分离
     */
    @Test
    public void testMultiThreadReadWriteSeparation() throws Exception {
        LOGGER.info("开始测试多线程环境下的读写分离...");

        int threadCount = 5;
        List<Thread> threads = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();

        // 创建多个线程，每个线程执行读写操作
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            Thread thread = new Thread(() -> {
                try {
                    // 只读操作
                    ReadWriteSeparateDataSource.setReadOnly(true);
                    try (Connection conn = dataSource.getConnection()) {
                        String sql = "SELECT COUNT(*) FROM tb_test_user";
                        try (
                                Statement stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery(sql)
                        ) {
                            Assertions.assertTrue(rs.next());
                            LOGGER.debug("线程 {} 从库查询成功", threadId);
                        }
                    }

                    // 写操作
                    ReadWriteSeparateDataSource.setReadOnly(false);
                    try (Connection conn = dataSource.getConnection()) {
                        conn.setAutoCommit(false);
                        String sql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setString(1, "thread_user_" + threadId);
                            pstmt.setString(2, "thread" + threadId + "@example.com");
                            pstmt.setInt(3, 20 + threadId);
                            pstmt.executeUpdate();
                        }
                        conn.rollback(); // 回滚测试数据
                        LOGGER.debug("线程 {} 主库写入成功", threadId);
                    }

                    // 清除状态
                    ReadWriteSeparateDataSource.clearReadOnly();
                }
                catch (Exception e) {
                    synchronized (exceptions) {
                        exceptions.add(e);
                    }
                }
            });
            threads.add(thread);
        }

        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }

        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }

        // 验证没有异常
        Assertions.assertTrue(exceptions.isEmpty(),
                              "多线程测试应该没有异常，但发现 " + exceptions.size() + " 个异常: " + exceptions);

        LOGGER.info("多线程环境下的读写分离测试通过，{} 个线程全部成功", threadCount);
    }
}
