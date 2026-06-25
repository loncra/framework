package io.github.loncra.framework.mybatis.interceptor.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 操作数据追踪拦截器
 *
 * @author maurice.chen
 */
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
)
public class OperationDataTraceInterceptor implements Interceptor {

    public static final String OPERATION_TRACE_OBSERVATION_NAME = "loncra.operation.trace";

    /**
     * 移除转义字符的正则表达式
     */
    public static final String REMOVE_ESCAPE_REG = "\\\\.|\\n|\\t";

    /**
     * 需要追踪的 SQL 命令类型列表
     */
    private static final List<SqlCommandType> SQL_COMMAND_TYPES = Arrays.asList(SqlCommandType.INSERT, SqlCommandType.UPDATE, SqlCommandType.DELETE);

    /**
     * 操作数据追踪解析器
     */
    private final OperationDataTraceRepository operationDataTraceRepository;

    /**
     * Observation 注册表
     */
    private final ObservationRegistry observationRegistry;

    /**
     * 创建一个操作数据追踪拦截器
     *
     * @param operationDataTraceRepository 操作数据追踪解析器
     * @param observationRegistry          Observation 注册表，可为 null
     */
    public OperationDataTraceInterceptor(
            OperationDataTraceRepository operationDataTraceRepository,
            ObservationRegistry observationRegistry
    ) {
        this.operationDataTraceRepository = operationDataTraceRepository;
        this.observationRegistry = observationRegistry;
    }

    /**
     * 拦截执行更新操作
     *
     * @param invocation 方法调用
     *
     * @return 执行结果
     *
     * @throws Throwable 执行异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();

        if (!Integer.class.isAssignableFrom(result.getClass())) {
            return result;
        }

        // 如果影响行号不大于 0 什么都不做.
        Integer affectedNumber = CastUtils.cast(result);
        if (affectedNumber <= 0) {
            return result;
        }

        MappedStatement mappedStatement = CastUtils.cast(invocation.getArgs()[0]);
        if (!SQL_COMMAND_TYPES.contains(mappedStatement.getSqlCommandType())) {
            return result;
        }

        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(parameter);
        String sql = RegExUtils.replaceAll(boundSql.getSql(), REMOVE_ESCAPE_REG, StringUtils.SPACE);

        Statement statement = CCJSqlParserUtil.parse(sql);

        List<OperationDataTraceRecord> records = operationDataTraceRepository.createOperationDataTraceRecord(mappedStatement, statement, parameter);

        if (CollectionUtils.isNotEmpty(records)) {
            Runnable saveAction = () -> saveRecords(records);
            if (observationRegistry != null) {
                OperationDataTraceRecord first = records.getFirst();
                Observation.createNotStarted(OPERATION_TRACE_OBSERVATION_NAME, observationRegistry)
                        .lowCardinalityKeyValue("db.operation.type", mappedStatement.getSqlCommandType().name())
                        .lowCardinalityKeyValue("db.operation.target", first.getData().getTarget())
                        .observe(saveAction);
            }
            else {
                saveAction.run();
            }
        }

        return result;
    }

    private void saveRecords(List<OperationDataTraceRecord> records) {
        Map<String, List<OperationDataTraceRecord>> grouping = records.stream()
                .collect(Collectors.groupingBy(g -> g.getData().getTarget()));
        for (Map.Entry<String, List<OperationDataTraceRecord>> entry : grouping.entrySet()) {
            operationDataTraceRepository.getOperationDataTraceRecordHooks()
                    .stream()
                    .filter(s -> s.isSupport(entry.getKey()))
                    .forEach(s -> entry.getValue().forEach(s::preSaveOperationDataTraceRecord));
        }
        operationDataTraceRepository.saveOperationDataTraceRecord(records);
    }

}
