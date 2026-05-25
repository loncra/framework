package io.github.loncra.framework.mybatis.interceptor.audit;

import net.sf.jsqlparser.statement.Statement;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;

/**
 * 操作数据留痕仓库：负责从 MyBatis 写操作上下文<b>创建</b>留痕记录，并<b>保存</b>（落库或发布审计事件）。
 * <p>由 {@link OperationDataTraceInterceptor} 在 {@code Executor#update} 成功且影响行数 &gt; 0 时调用：
 * 先 {@link #createOperationDataTraceRecord}，再经 {@link OperationDataTraceRecordHook} 的 {@code preSave}，最后 {@link #saveOperationDataTraceRecord}。</p>
 * <p>默认实现见 {@code spring-boot-starter-mybatis-plus} 的 {@code MybatisPlusOperationDataTraceRepository}；
 * 与安全控制器审计合并见 {@code SecurityPrincipalOperationDataTraceRepository}。</p>
 *
 * @author maurice.chen
 * @see OperationDataTraceRecordHook
 * @see AbstractOperationDataTraceRepository
 */
public interface OperationDataTraceRepository {

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

    /**
     * 获取操作数据留痕钩子
     *
     * @return 操作数据留痕钩子
     */
    List<OperationDataTraceRecordHook> getOperationDataTraceRecordHooks();
}