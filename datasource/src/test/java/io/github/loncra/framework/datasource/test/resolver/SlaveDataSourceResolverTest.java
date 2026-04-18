package io.github.loncra.framework.datasource.test.resolver;

import io.github.loncra.framework.datasource.resolver.DataSourceObtainResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 从库数据源解析器测试基类
 *
 * @author maurice.chen
 */
public abstract class SlaveDataSourceResolverTest {

    protected DataSource masterDataSource;
    protected List<DataSource> slaveDataSources;
    protected DataSourceObtainResolver resolver;

    @BeforeEach
    public void setUp() throws Exception {
        masterDataSource = createH2DataSource("master");
        slaveDataSources = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            slaveDataSources.add(createH2DataSource("slave" + i));
        }
        resolver = createResolver();
    }

    /**
     * 创建解析器实例
     *
     * @return 解析器实例
     */
    protected abstract DataSourceObtainResolver createResolver();

    /**
     * 创建 H2 数据源
     */
    protected DataSource createH2DataSource(String dbName) throws Exception {
        Class<?> hikariClass = Class.forName("com.zaxxer.hikari.HikariDataSource");
        DataSource ds = (DataSource) hikariClass.getDeclaredConstructor().newInstance();

        setProperty(ds, "jdbcUrl", "jdbc:h2:mem:" + dbName + ";MODE=MYSQL;DB_CLOSE_DELAY=-1");
        setProperty(ds, "username", "sa");
        setProperty(ds, "password", "");
        setProperty(ds, "driverClassName", "org.h2.Driver");

        return ds;
    }

    /**
     * 设置对象属性
     */
    protected void setProperty(
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
     * 测试从库列表为空时返回主库
     */
    @Test
    public void testResolveWithEmptySlaveList() {
        List<DataSource> emptyList = new ArrayList<>();
        DataSource result = resolver.obtain(emptyList, masterDataSource);
        assertEquals(masterDataSource, result, "从库列表为空时应返回主库");
    }

    /**
     * 测试从库列表为 null 时返回主库
     */
    @Test
    public void testResolveWithNullSlaveList() {
        DataSource result = resolver.obtain(null, masterDataSource);
        assertEquals(masterDataSource, result, "从库列表为 null 时应返回主库");
    }

    /**
     * 测试解析器名称
     */
    @Test
    public void testGetName() {
        assertNotNull(resolver.getName(), "解析器名称不应为 null");
        assertFalse(resolver.getName().isEmpty(), "解析器名称不应为空");
    }
}
