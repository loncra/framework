package io.github.loncra.framework.mybatis.interceptor.audit;

import net.sf.jsqlparser.statement.Statement;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;

/**
 * 操作数据留痕仓库
 *
 * @author maurice.chen
 */
public interface OperationDataTraceResolver {

    /**
     * 创建操作数据留痕记录
     *
     * @param mappedStatement Mapped Statement
     * @param statement       sql ast
     * @param parameter       当前mybatis 参数
     *
     * @return 操作数据留痕数据
     */
    List<OperationDataTraceRecord> createOperationDataTraceRecord(
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception;

    /**
     * 保存操作数据留痕记录
     *
     * @param records 操作数据留痕记录
     */
    void saveOperationDataTraceRecord(List<OperationDataTraceRecord> records) throws Exception;

}