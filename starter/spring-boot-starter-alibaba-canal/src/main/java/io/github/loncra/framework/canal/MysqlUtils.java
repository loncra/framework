package io.github.loncra.framework.canal;

import io.github.loncra.framework.canal.domain.meta.TableColumnInfoMetadata;
import io.github.loncra.framework.commons.NamingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * MySQL 工具类
 *
 * @author maurice.chen
 */
public class MysqlUtils {

    /**
     * 日志记录器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlUtils.class);

    /**
     * 获取表列信息
     *
     * @param tableName    表名
     * @param databaseName 数据库名
     * @param connection   数据库连接
     *
     * @return 表列信息元数据列表
     */
    public static List<TableColumnInfoMetadata> getTableColumns(
            String tableName,
            String databaseName,
            Connection connection
    ) {

        List<TableColumnInfoMetadata> columns = new LinkedList<>();


        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("SELECT COLUMN_NAME, COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_NAME = ? AND TABLE_SCHEMA = ?");
            statement.setString(1, tableName);
            statement.setString(2, databaseName);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String columnName = resultSet.getString(TableColumnInfoMetadata.MYSQL_COLUMN_NAME);
                String columnComment = resultSet.getString(TableColumnInfoMetadata.MYSQL_COLUMN_COMMENT);
                String camelName = NamingUtils.castSnakeCaseToCamelCase(columnName);

                TableColumnInfoMetadata tableColumnInfoMetadata = new TableColumnInfoMetadata();

                tableColumnInfoMetadata.setComment(columnComment);
                tableColumnInfoMetadata.setId(camelName);
                tableColumnInfoMetadata.setName(columnName);

                columns.add(tableColumnInfoMetadata);
            }
        }
        catch (Exception e) {
            LOGGER.warn("读取 {} 表的列内容出现错误", tableName, e);
        }
        finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return columns;
    }
}
