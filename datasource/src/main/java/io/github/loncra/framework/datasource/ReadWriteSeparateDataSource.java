package io.github.loncra.framework.datasource;

import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import io.github.loncra.framework.datasource.resolver.support.RandomDataSourceObtainResolver;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * 读写分离数据源
 * <p>
 * 完全无依赖的读写分离数据源实现，根据 Connection.isReadOnly() 自动路由到主库或从库：
 * <p>
 * - 如果连接设置了 isReadOnly = true，路由到从库
 * <p>
 * - 如果连接设置了 isReadOnly = false 或未设置，路由到主库
 * <p>
 * 使用方式：
 * <pre>
 * Connection conn = dataSource.getConnection();
 * conn.setReadOnly(true);  // 设置为只读，后续操作会路由到从库
 * // 执行查询操作...
 * conn.setReadOnly(false); // 设置为非只读，后续操作会路由到主库
 * // 执行写操作...
 * </pre>
 *
 * @author maurice.chen
 */
public class ReadWriteSeparateDataSource implements DataSource {

    /**
     * 线程本地变量，存储当前线程的只读状态
     */
    private static final ThreadLocal<Boolean> READ_ONLY_CONTEXT = new ThreadLocal<>();

    /**
     * 主数据源
     */
    private DataSource masterDataSource;

    /**
     * 从数据源列表
     */
    private List<DataSource> slaveDataSources;

    /**
     * 从库数据源解析器
     */
    private DataSourceObtainResolver dataSourceObtainResolver;

    /**
     * 日志写入器
     */
    private PrintWriter logWriter;

    /**
     * 登录超时时间（秒）
     */
    private int loginTimeout = 0;

    /**
     * 构造函数
     *
     * @param masterDataSource 主数据源
     */
    public ReadWriteSeparateDataSource(DataSource masterDataSource) {
        this(masterDataSource, new LinkedList<>(), new RandomDataSourceObtainResolver());
    }

    /**
     * 构造函数
     *
     * @param masterDataSource 主数据源
     */
    public ReadWriteSeparateDataSource(
            DataSource masterDataSource,
            DataSourceObtainResolver dataSourceObtainResolver
    ) {
        this(masterDataSource, new LinkedList<>(), dataSourceObtainResolver);
    }

    /**
     * 构造函数
     *
     * @param masterDataSource 主数据源
     * @param slaveDataSources 从数据源列表
     */
    public ReadWriteSeparateDataSource(
            DataSource masterDataSource,
            List<DataSource> slaveDataSources
    ) {
        this(masterDataSource, slaveDataSources, new RandomDataSourceObtainResolver());
    }

    /**
     * 构造函数
     *
     * @param masterDataSource         主数据源
     * @param slaveDataSources         从数据源列表
     * @param dataSourceObtainResolver 从库数据源解析器
     */
    public ReadWriteSeparateDataSource(
            DataSource masterDataSource,
            List<DataSource> slaveDataSources,
            DataSourceObtainResolver dataSourceObtainResolver
    ) {
        this.masterDataSource = masterDataSource;
        this.slaveDataSources = slaveDataSources;
        this.dataSourceObtainResolver = dataSourceObtainResolver;
        initialize();
    }

    /**
     * 初始化数据源
     */
    private void initialize() {
        SystemException.isTrue(Objects.nonNull(masterDataSource), "主数据源不能为空");

        if (slaveDataSources == null || slaveDataSources.isEmpty()) {
            slaveDataSources = List.of(masterDataSource);
        }
    }

    /**
     * 获取连接
     * <p>
     * 根据当前线程的只读状态选择主库或从库
     *
     * @return 数据库连接（包装后的连接，支持动态路由）
     *
     * @throws SQLException SQL 异常
     */
    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(null, null);
    }

    /**
     * 获取连接（带用户名和密码）
     * <p>
     * 根据当前线程的只读状态选择主库或从库
     *
     * @param username 用户名
     * @param password 密码
     *
     * @return 数据库连接（包装后的连接，支持动态路由）
     *
     * @throws SQLException SQL 异常
     */
    @Override
    public Connection getConnection(
            String username,
            String password
    ) throws SQLException {
        // 获取当前线程的只读状态
        Boolean readOnly = READ_ONLY_CONTEXT.get();

        // 选择数据源
        DataSource targetDataSource = selectDataSource(readOnly);

        // 获取连接
        Connection connection;
        if (StringUtils.isNoneEmpty(username, password)) {
            connection = targetDataSource.getConnection(username, password);
        }
        else {
            connection = targetDataSource.getConnection();
        }

        // 如果当前线程设置了只读状态，同步设置连接的只读状态
        if (readOnly != null) {
            connection.setReadOnly(readOnly);
        }

        // 返回包装的连接，支持动态路由
        return new ReadWriteSeparateConnection(connection);
    }

    /**
     * 选择数据源
     * <p>
     * 根据只读状态选择主库或从库
     *
     * @param readOnly 是否只读
     *
     * @return 目标数据源
     */
    private DataSource selectDataSource(Boolean readOnly) {
        if (readOnly != null && readOnly) {
            // 只读操作，选择从库
            return selectSlaveDataSource();
        }
        else {
            // 写操作或未设置，使用主库
            return masterDataSource;
        }
    }

    /**
     * 选择从数据源
     * <p>
     * 使用配置的解析器选择从库
     *
     * @return 从数据源
     */
    private DataSource selectSlaveDataSource() {
        return dataSourceObtainResolver.obtain(slaveDataSources, masterDataSource);
    }

    /**
     * 设置当前线程的只读状态
     * <p>
     * 设置后，后续获取的连接会根据此状态路由到主库或从库
     *
     * @param readOnly 是否只读
     */
    public static void setReadOnly(boolean readOnly) {
        READ_ONLY_CONTEXT.set(readOnly);
    }

    /**
     * 清除当前线程的只读状态
     */
    public static void clearReadOnly() {
        READ_ONLY_CONTEXT.remove();
    }

    /**
     * 获取当前线程的只读状态
     *
     * @return 是否只读，如果未设置则返回 null
     */
    public static Boolean getReadOnly() {
        return READ_ONLY_CONTEXT.get();
    }

    /**
     * 设置主数据源
     *
     * @param masterDataSource 主数据源
     */
    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    /**
     * 设置从数据源列表
     *
     * @param slaveDataSources 从数据源列表
     */
    public void setSlaveDataSources(List<DataSource> slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }

    /**
     * 设置从库数据源解析器
     *
     * @param dataSourceObtainResolver 从库数据源解析器
     */
    public void setSlaveDataSourceObtainResolver(DataSourceObtainResolver dataSourceObtainResolver) {
        this.dataSourceObtainResolver = Objects.requireNonNullElse(dataSourceObtainResolver, new RandomDataSourceObtainResolver());
    }

    /**
     * 获取从库数据源解析器
     *
     * @return 从库数据源解析器
     */
    public DataSourceObtainResolver getSlaveDataSourceObtainResolver() {
        return dataSourceObtainResolver;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.loginTimeout = seconds;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return loginTimeout;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("getParentLogger() is not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return iface.cast(this);
        }
        throw new SQLException("DataSource is not a wrapper for " + iface.getName());
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
}
