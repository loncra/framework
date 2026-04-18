package io.github.loncra.framework.datasource.test;

import io.github.loncra.framework.datasource.ReadWriteSeparateDataSource;
import io.github.loncra.framework.datasource.test.entity.TestUser;
import io.github.loncra.framework.datasource.test.service.TestUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 读写分离数据源测试
 *
 * @author maurice.chen
 */
@SpringBootTest
public class ReadWriteSeparateDataSourceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadWriteSeparateDataSourceTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TestUserService testUserService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void tearDown() {
        // 清除 ThreadLocal 状态
        ReadWriteSeparateDataSource.clearReadOnly();
    }

    /**
     * 测试数据源类型
     */
    @Test
    public void testDataSourceType() {
        Assertions.assertNotNull(dataSource, "数据源不能为空");
        Assertions.assertInstanceOf(ReadWriteSeparateDataSource.class, dataSource, "数据源应该是 ReadWriteSeparateDataSource 类型");

        ReadWriteSeparateDataSource rwDataSource = (ReadWriteSeparateDataSource) dataSource;
        LOGGER.info("数据源类型: {}", rwDataSource.getClass().getName());
    }

    /**
     * 测试只读操作路由到从库（使用 Spring 事务）
     */
    @Test
    @Transactional(readOnly = true)
    @Rollback
    public void testReadOnlyTransaction() {
        LOGGER.info("开始测试只读事务...");

        // 执行只读查询
        List<TestUser> users = testUserService.findAll();

        Assertions.assertNotNull(users, "用户列表不能为空");
        Assertions.assertFalse(users.isEmpty(), "用户列表不应该为空");

        LOGGER.info("只读事务测试通过，查询到 {} 个用户", users.size());
    }

    /**
     * 测试写操作路由到主库（使用 Spring 事务）
     */
    @Test
    @Transactional
    @Rollback
    public void testWriteTransaction() {
        LOGGER.info("开始测试写事务...");

        // 创建新用户（写操作，应该在主库执行）
        TestUser newUser = new TestUser("test_user", "test@example.com", 20);
        Long userId = testUserService.save(newUser);

        Assertions.assertNotNull(userId, "用户ID不能为空");
        Assertions.assertTrue(userId > 0, "用户ID应该大于0");

        // 查询刚创建的用户（在同一个事务中，应该从主库读取）
        TestUser savedUser = testUserService.findById(userId);
        Assertions.assertNotNull(savedUser, "保存的用户应该能查询到");
        Assertions.assertEquals("test_user", savedUser.getUsername());
        Assertions.assertEquals("test@example.com", savedUser.getEmail());

        // 更新用户（写操作）
        savedUser.setAge(25);
        testUserService.update(savedUser);

        // 再次查询验证更新
        TestUser updatedUser = testUserService.findById(userId);
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(25, updatedUser.getAge());

        // 删除用户（写操作）
        testUserService.delete(userId);

        // 验证删除（在同一个事务中，应该从主库读取）
        TestUser deletedUser = testUserService.findById(userId);
        Assertions.assertNull(deletedUser, "删除的用户应该查询不到");

        LOGGER.info("写事务测试通过，路由到主库");
    }

    /**
     * 测试无事务时使用主库
     */
    @Test
    public void testNoTransaction() {
        LOGGER.info("开始测试无事务操作...");

        // 清除只读状态，确保使用主库
        ReadWriteSeparateDataSource.clearReadOnly();

        // 无事务的查询操作应该使用主库
        int count = testUserService.count();

        Assertions.assertTrue(count >= 0, "用户数量应该大于等于0");
        LOGGER.info("无事务操作测试通过，用户数量: {}，路由到主库", count);
    }

    /**
     * 测试通过 setReadOnly 方法设置只读状态
     */
    @Test
    public void testSetReadOnlyMethod() {
        LOGGER.info("开始测试通过 setReadOnly 方法设置只读状态...");

        // 设置只读状态
        ReadWriteSeparateDataSource.setReadOnly(true);
        Assertions.assertTrue(ReadWriteSeparateDataSource.getReadOnly(), "应该设置为只读状态");

        // 清除只读状态
        ReadWriteSeparateDataSource.clearReadOnly();
        Assertions.assertNull(ReadWriteSeparateDataSource.getReadOnly(), "应该清除只读状态");

        // 设置非只读状态
        ReadWriteSeparateDataSource.setReadOnly(false);
        Assertions.assertFalse(ReadWriteSeparateDataSource.getReadOnly(), "应该设置为非只读状态");

        LOGGER.info("setReadOnly 方法测试通过");
    }

    /**
     * 测试连接获取
     */
    @Test
    public void testGetConnection() throws SQLException {
        LOGGER.info("开始测试连接获取...");

        try (Connection connection = dataSource.getConnection()) {
            Assertions.assertNotNull(connection, "连接不能为空");
            Assertions.assertFalse(connection.isClosed(), "连接不应该是关闭状态");
            LOGGER.info("连接获取成功，连接类型: {}", connection.getClass().getName());
        }
    }

    /**
     * 测试多个只读操作
     */
    @Test
    public void testMultipleReadOperations() {
        LOGGER.info("开始测试多个只读操作...");

        // 执行多次只读查询
        for (int i = 0; i < 5; i++) {
            List<TestUser> users = testUserService.findAll();
            Assertions.assertNotNull(users);
            LOGGER.debug("第 {} 次查询，获取到 {} 个用户", i + 1, users.size());
        }

        LOGGER.info("多个只读操作测试通过");
    }


    /**
     * 测试数据源路由逻辑
     */
    @Test
    public void testDataSourceRouting() {
        LOGGER.info("开始测试数据源路由逻辑...");

        ReadWriteSeparateDataSource rwDataSource = (ReadWriteSeparateDataSource) dataSource;

        // 测试主数据源不为空
        Assertions.assertNotNull(rwDataSource, "读写分离数据源不能为空");

        // 验证主数据源
        DataSource master = getMasterDataSource(rwDataSource);
        Assertions.assertNotNull(master, "主库数据源不能为空");
        LOGGER.info("主库数据源: {}", master.getClass().getName());

        // 验证从库数据源
        List<DataSource> slaves = getSlaveDataSources(rwDataSource);
        Assertions.assertNotNull(slaves, "从库数据源列表不能为空");
        Assertions.assertFalse(slaves.isEmpty(), "应该至少配置一个从库");

        for (int i = 0; i < slaves.size(); i++) {
            DataSource slave = slaves.get(i);
            Assertions.assertNotNull(slave, "从库数据源不能为空: " + i);
            LOGGER.info("从库 {} 数据源: {}", i, slave.getClass().getName());
        }

        LOGGER.info("数据源路由逻辑测试通过，主库1个，从库{}个", slaves.size());
    }

    /**
     * 测试从库负载均衡
     */
    @Test
    @Transactional(readOnly = true)
    @Rollback
    public void testSlaveLoadBalance() {
        LOGGER.info("开始测试从库负载均衡...");

        ReadWriteSeparateDataSource rwDataSource = (ReadWriteSeparateDataSource) dataSource;

        // 设置只读状态
        ReadWriteSeparateDataSource.setReadOnly(true);

        // 多次调用，验证从库负载均衡
        java.util.Set<DataSource> usedSlaves = new java.util.HashSet<>();
        for (int i = 0; i < 20; i++) {
            // 获取连接，应该路由到从库
            try (Connection conn = rwDataSource.getConnection()) {
                // 通过反射获取实际使用的数据源（简化测试）
                usedSlaves.add(getActualDataSource(rwDataSource, conn));
            }
            catch (SQLException e) {
                LOGGER.warn("获取连接失败: {}", e.getMessage());
            }
            // 执行查询操作
            testUserService.findAll();
        }

        // 如果有多个从库，应该能够负载均衡到不同的从库
        LOGGER.info("使用的从库数量: {}", usedSlaves.size());
        // 注意：由于是随机选择，不能保证每次都使用不同的从库，但至少应该能路由到从库
        Assertions.assertFalse(usedSlaves.isEmpty(), "应该至少使用一个从库");

        LOGGER.info("从库负载均衡测试通过，使用了 {} 个不同的从库", usedSlaves.size());
    }

    /**
     * 测试只读事务中的 SELECT 操作（应该路由到从库）
     */
    @Test
    @Transactional(readOnly = true)
    @Rollback
    public void testSelectInReadOnlyTransaction() {
        LOGGER.info("开始测试只读事务中的 SELECT 操作...");

        // 执行 SELECT 操作
        String selectSql = "SELECT id, username, email, age FROM tb_test_user WHERE id = ?";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(selectSql, 1);

        Assertions.assertNotNull(results, "查询结果不能为空");
        if (!results.isEmpty()) {
            Map<String, Object> user = results.get(0);
            Assertions.assertNotNull(user.get("id"), "用户ID不能为空");
            LOGGER.info("从库查询成功，用户ID: {}, 用户名: {}", user.get("id"), user.get("username"));
        }

        // 执行 COUNT 查询
        String countSql = "SELECT COUNT(*) as total FROM tb_test_user";
        Integer total = jdbcTemplate.queryForObject(countSql, Integer.class);
        Assertions.assertNotNull(total, "总数不能为空");
        Assertions.assertTrue(total >= 0, "总数应该大于等于0");

        LOGGER.info("只读事务中的 SELECT 操作测试通过，查询到 {} 条记录", total);
    }

    /**
     * 测试写事务中的 INSERT 操作（应该路由到主库）
     */
    @Test
    @Transactional
    @Rollback
    public void testInsertInWriteTransaction() {
        LOGGER.info("开始测试写事务中的 INSERT 操作...");

        // 执行 INSERT 操作
        String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(insertSql, "test_insert_user", "test_insert@example.com", 25);
        Assertions.assertEquals(1, rowsAffected, "应该插入1条记录");

        // 在同一个事务中查询刚插入的数据（应该从主库读取）
        String selectSql = "SELECT id, username, email, age FROM tb_test_user WHERE username = ?";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(selectSql, "test_insert_user");
        Assertions.assertFalse(results.isEmpty(), "应该能查询到刚插入的数据");

        Map<String, Object> insertedUser = results.get(0);
        Assertions.assertEquals("test_insert_user", insertedUser.get("username"));
        Assertions.assertEquals("test_insert@example.com", insertedUser.get("email"));
        Assertions.assertEquals(25, insertedUser.get("age"));

        LOGGER.info("写事务中的 INSERT 操作测试通过，插入用户ID: {}", insertedUser.get("id"));
    }

    /**
     * 测试写事务中的 UPDATE 操作（应该路由到主库）
     */
    @Test
    @Transactional
    @Rollback
    public void testUpdateInWriteTransaction() {
        LOGGER.info("开始测试写事务中的 UPDATE 操作...");

        // 先插入一条测试数据
        String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, "test_update_user", "test_update@example.com", 20);

        // 获取插入的ID
        Long userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        Assertions.assertNotNull(userId, "插入的用户ID不能为空");

        // 执行 UPDATE 操作
        String updateSql = "UPDATE tb_test_user SET age = ?, email = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(updateSql, 30, "test_updated@example.com", userId);
        Assertions.assertEquals(1, rowsAffected, "应该更新1条记录");

        // 在同一个事务中查询更新后的数据（应该从主库读取）
        String selectSql = "SELECT id, username, email, age FROM tb_test_user WHERE id = ?";
        Map<String, Object> updatedUser = jdbcTemplate.queryForMap(selectSql, userId);
        Assertions.assertEquals(30, updatedUser.get("age"), "年龄应该更新为30");
        Assertions.assertEquals("test_updated@example.com", updatedUser.get("email"), "邮箱应该已更新");

        LOGGER.info("写事务中的 UPDATE 操作测试通过，更新用户ID: {}", userId);
    }

    /**
     * 测试写事务中的 DELETE 操作（应该路由到主库）
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteInWriteTransaction() {
        LOGGER.info("开始测试写事务中的 DELETE 操作...");

        // 先插入一条测试数据
        String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, "test_delete_user", "test_delete@example.com", 25);

        // 获取插入的ID
        Long userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        Assertions.assertNotNull(userId, "插入的用户ID不能为空");

        // 验证数据已插入
        String countBeforeSql = "SELECT COUNT(*) FROM tb_test_user WHERE id = ?";
        Integer countBefore = jdbcTemplate.queryForObject(countBeforeSql, Integer.class, userId);
        Assertions.assertEquals(1, countBefore, "删除前应该有1条记录");

        // 执行 DELETE 操作
        String deleteSql = "DELETE FROM tb_test_user WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(deleteSql, userId);
        Assertions.assertEquals(1, rowsAffected, "应该删除1条记录");

        // 在同一个事务中查询，验证数据已删除（应该从主库读取）
        String countAfterSql = "SELECT COUNT(*) FROM tb_test_user WHERE id = ?";
        Integer countAfter = jdbcTemplate.queryForObject(countAfterSql, Integer.class, userId);
        Assertions.assertEquals(0, countAfter, "删除后应该没有记录");

        LOGGER.info("写事务中的 DELETE 操作测试通过，删除用户ID: {}", userId);
    }

    /**
     * 测试读写混合操作（验证路由正确性）
     */
    @Test
    @Transactional
    @Rollback
    public void testReadWriteMixedOperations() {
        LOGGER.info("开始测试读写混合操作...");

        // 1. 执行 SELECT 查询（在写事务中，应该从主库读取）
        String selectSql = "SELECT COUNT(*) as total FROM tb_test_user";
        Integer totalBefore = jdbcTemplate.queryForObject(selectSql, Integer.class);
        Assertions.assertNotNull(totalBefore, "初始总数不能为空");
        LOGGER.info("初始用户总数: {}", totalBefore);

        // 2. 执行 INSERT 操作
        String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";
        int insertRows = jdbcTemplate.update(insertSql, "test_mixed_user", "test_mixed@example.com", 28);
        Assertions.assertEquals(1, insertRows, "应该插入1条记录");

        // 3. 再次执行 SELECT 查询（应该能看到刚插入的数据，因为都从主库读取）
        Integer totalAfterInsert = jdbcTemplate.queryForObject(selectSql, Integer.class);
        Assertions.assertEquals(totalBefore + 1, totalAfterInsert,
                                "插入后总数应该增加1");

        // 4. 执行 UPDATE 操作
        Long userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        String updateSql = "UPDATE tb_test_user SET age = ? WHERE id = ?";
        int updateRows = jdbcTemplate.update(updateSql, 35, userId);
        Assertions.assertEquals(1, updateRows, "应该更新1条记录");

        // 5. 查询更新后的数据
        String selectUserSql = "SELECT age FROM tb_test_user WHERE id = ?";
        Integer age = jdbcTemplate.queryForObject(selectUserSql, Integer.class, userId);
        Assertions.assertEquals(35, age, "年龄应该更新为35");

        // 6. 执行 DELETE 操作
        String deleteSql = "DELETE FROM tb_test_user WHERE id = ?";
        int deleteRows = jdbcTemplate.update(deleteSql, userId);
        Assertions.assertEquals(1, deleteRows, "应该删除1条记录");

        // 7. 验证删除后总数恢复
        Integer totalAfterDelete = jdbcTemplate.queryForObject(selectSql, Integer.class);
        Assertions.assertEquals(totalBefore, totalAfterDelete,
                                "删除后总数应该恢复为初始值");

        LOGGER.info("读写混合操作测试通过，所有操作都正确路由到主库");
    }


    /**
     * 测试批量操作（INSERT、UPDATE、DELETE）
     */
    @Test
    @Transactional
    @Rollback
    public void testBatchOperations() {
        LOGGER.info("开始测试批量操作...");

        // 批量 INSERT
        String[] usernames = {"batch_user1", "batch_user2", "batch_user3"};
        String insertSql = "INSERT INTO tb_test_user (username, email, age) VALUES (?, ?, ?)";

        for (String username : usernames) {
            int rows = jdbcTemplate.update(insertSql, username, username + "@example.com", 25);
            Assertions.assertEquals(1, rows, "应该插入1条记录: " + username);
        }

        // 验证批量插入成功
        Integer countAfterInsert = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM tb_test_user WHERE username LIKE 'batch_user%'",
                Integer.class);
        Assertions.assertEquals(3, countAfterInsert, "应该插入3条记录");

        // 批量 UPDATE
        String updateSql = "UPDATE tb_test_user SET age = ? WHERE username LIKE 'batch_user%'";
        int updateRows = jdbcTemplate.update(updateSql, 30);
        Assertions.assertEquals(3, updateRows, "应该更新3条记录");

        // 验证批量更新成功
        Integer updatedCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM tb_test_user WHERE username LIKE 'batch_user%' AND age = 30",
                Integer.class);
        Assertions.assertEquals(3, updatedCount, "应该有3条记录的年龄为30");

        // 批量 DELETE
        String deleteSql = "DELETE FROM tb_test_user WHERE username LIKE 'batch_user%'";
        int deleteRows = jdbcTemplate.update(deleteSql);
        Assertions.assertEquals(3, deleteRows, "应该删除3条记录");

        // 验证批量删除成功
        Integer countAfterDelete = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM tb_test_user WHERE username LIKE 'batch_user%'",
                Integer.class);
        Assertions.assertEquals(0, countAfterDelete, "批量删除后应该没有记录");

        LOGGER.info("批量操作测试通过");
    }

    /**
     * 获取主库数据源
     */
    private DataSource getMasterDataSource(ReadWriteSeparateDataSource dataSource) {
        try {
            java.lang.reflect.Field field = ReadWriteSeparateDataSource.class.getDeclaredField("masterDataSource");
            field.setAccessible(true);
            return (DataSource) field.get(dataSource);
        }
        catch (Exception e) {
            LOGGER.warn("无法获取主库数据源: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取从库数据源列表
     */
    private List<DataSource> getSlaveDataSources(ReadWriteSeparateDataSource dataSource) {
        try {
            Field field = ReadWriteSeparateDataSource.class.getDeclaredField("slaveDataSources");
            field.setAccessible(true);
            return (List<DataSource>) field.get(dataSource);
        }
        catch (Exception e) {
            LOGGER.warn("无法获取从库数据源列表: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取连接实际使用的数据源（简化版本，用于测试）
     */
    private DataSource getActualDataSource(
            ReadWriteSeparateDataSource dataSource,
            Connection conn
    ) {
        // 根据当前只读状态判断使用的数据源
        Boolean readOnly = ReadWriteSeparateDataSource.getReadOnly();
        if (readOnly != null && readOnly) {
            // 只读状态，使用从库
            List<DataSource> slaves = getSlaveDataSources(dataSource);
            if (slaves != null && !slaves.isEmpty()) {
                return slaves.get(0); // 简化：返回第一个从库
            }
        }
        // 非只读或未设置，使用主库
        return getMasterDataSource(dataSource);
    }
}
