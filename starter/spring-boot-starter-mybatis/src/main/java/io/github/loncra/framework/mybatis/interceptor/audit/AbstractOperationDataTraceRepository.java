package io.github.loncra.framework.mybatis.interceptor.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata;
import io.github.loncra.framework.mybatis.enumerate.OperationDataType;
import io.github.loncra.framework.security.audit.SpringElStoragePositioningGenerator;
import io.github.loncra.framework.security.audit.StoragePositioningGenerator;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 抽象的操作数据留痕仓库：按 JSQLParser 解析的 Insert/Update/Delete 生成 {@link OperationDataTraceRecord}。
 * <p>业务载荷在嵌套的 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata}（{@code target}、{@code type}、{@code data}、可选 {@code remark}）；
 * 记录信封含 {@code principal}（缺省本机 IP）、{@code creationTime}、可选 {@code storagePositioning}。</p>
 * <p>若配置 {@link io.github.loncra.framework.mybatis.config.OperationDataTraceProperties#getStoragePosition()}，对每条基础记录再 SpEL 生成带 {@code storagePositioning} 的<b>复制行</b>（基础行与复制行均进入后续 {@code save}，分别走默认桶与预计算分桶，见模块 README）。</p>
 * <p>框架<b>不</b>在 {@link #createBasicOperationDataTraceRecord} 内自动填充 {@code remark}；由注解或 {@link OperationDataTraceRecordHook} 填写。</p>
 *
 * @author maurice.chen
 * @see OperationDataTraceRecordHook
 */
public abstract class AbstractOperationDataTraceRepository implements OperationDataTraceRepository {


    /**
     * 操作数据追踪配置属性
     */
    private final OperationDataTraceProperties operationDataTraceProperties;

    /**
     * 解析器集合
     */
    private final List<OperationDataTraceRecordHook> operationDataTraceRecordHooks;

    /**
     * 存储定位生成器
     */
    private StoragePositioningGenerator storagePositioningGenerator;

    /**
     * 便捷构造：无 {@link OperationDataTraceRecordHook} 时等价于传入空列表。
     *
     * @param operationDataTraceProperties 操作数据追踪配置属性
     */
    public AbstractOperationDataTraceRepository(OperationDataTraceProperties operationDataTraceProperties) {
        this(operationDataTraceProperties, Collections.emptyList());
    }

    /**
     * 创建一个抽象的操作数据追踪解析器
     *
     * @param operationDataTraceProperties 操作数据追踪配置属性
     * @param operationDataTraceRecordHooks 留痕记录扩展解析器集合（可为空）
     */
    public AbstractOperationDataTraceRepository(
            OperationDataTraceProperties operationDataTraceProperties,
            List<OperationDataTraceRecordHook> operationDataTraceRecordHooks
    ) {
        this.operationDataTraceRecordHooks = Objects.requireNonNullElse(
                operationDataTraceRecordHooks,
                Collections.emptyList()
        );
        this.operationDataTraceProperties = operationDataTraceProperties;
        if (Objects.nonNull(operationDataTraceProperties.getStoragePosition())) {
            storagePositioningGenerator = new SpringElStoragePositioningGenerator(operationDataTraceProperties.getStoragePosition());
        }
    }

    @Override
    public List<OperationDataTraceRecord> createOperationDataTraceRecord(
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {
        List<OperationDataTraceRecord> createRecords = new LinkedList<>();
        if (statement instanceof Insert) {
            Insert insert = CastUtils.cast(statement);
            createRecords.addAll(createInsertRecord(insert, mappedStatement, statement, parameter));
        }
        else if (statement instanceof Update) {
            Update update = CastUtils.cast(statement);
            createRecords.addAll(createUpdateRecord(update, mappedStatement, statement, parameter));
        }
        else if (statement instanceof Delete) {
            Delete delete = CastUtils.cast(statement);
            createRecords.addAll(createDeleteRecord(delete, mappedStatement, statement, parameter));
        }

        List<OperationDataTraceRecord> result = new LinkedList<>(createRecords);
        if (Objects.nonNull(storagePositioningGenerator)) {
            List<OperationDataTraceRecord> storagePositioningRecords = new LinkedList<>();
            for (OperationDataTraceRecord record : createRecords) {
                String positioning = storagePositioningGenerator.generatePositioning(record);
                if (StringUtils.isEmpty(positioning)) {
                    continue;
                }
                OperationDataTraceRecord storagePositioning = CastUtils.of(record, record.getClass());
                storagePositioning.setStoragePositioning(positioning);
                storagePositioningRecords.add(storagePositioning);
                result.addAll(storagePositioningRecords);
            }
        }

        return result;
    }

    /**
     * 创建删除记录
     *
     * @param delete          SQL 删除语句
     * @param mappedStatement Mapped Statement
     * @param statement       SQL 语句
     * @param parameter       参数
     *
     * @return 操作数据追踪记录列表
     *
     * @throws Exception 创建异常
     */
    protected List<OperationDataTraceRecord> createDeleteRecord(
            Delete delete,
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {
        OperationDataTraceRecord result = createBasicOperationDataTraceRecord(
                OperationDataType.DELETE,
                delete.getTable().getName(),
                CastUtils.convertValue(parameter, CastUtils.MAP_TYPE_REFERENCE)
        );
        return Collections.singletonList(result);
    }

    /**
     * 创建更新记录
     *
     * @param update          SQL 更新语句
     * @param mappedStatement Mapped Statement
     * @param statement       SQL 语句
     * @param parameter       参数
     *
     * @return 操作数据追踪记录列表
     *
     * @throws Exception 创建异常
     */
    protected List<OperationDataTraceRecord> createUpdateRecord(
            Update update,
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {
        OperationDataTraceRecord result = createBasicOperationDataTraceRecord(
                OperationDataType.UPDATE,
                update.getTable().getName(),
                CastUtils.convertValue(parameter, CastUtils.MAP_TYPE_REFERENCE)
        );
        return Collections.singletonList(result);
    }

    /**
     * 创建插入记录
     *
     * @param insert          SQL 插入语句
     * @param mappedStatement Mapped Statement
     * @param statement       SQL 语句
     * @param parameter       参数
     *
     * @return 操作数据追踪记录列表
     *
     * @throws Exception 创建异常
     */
    protected List<OperationDataTraceRecord> createInsertRecord(
            Insert insert,
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {

        OperationDataTraceRecord result = createBasicOperationDataTraceRecord(
                OperationDataType.INSERT,
                insert.getTable().getName(),
                CastUtils.convertValue(parameter, CastUtils.MAP_TYPE_REFERENCE)
        );

        return Collections.singletonList(result);
    }

    /**
     * 创建基本的操作数据追踪记录
     *
     * @param type       操作数据类型
     * @param target     操作目标
     * @param data 提交的数据
     *
     * @return 操作数据追踪记录
     *
     * @throws UnknownHostException 获取主机地址异常
     */
    protected OperationDataTraceRecord createBasicOperationDataTraceRecord(
            OperationDataType type,
            String target,
            Map<String, Object> data
    ) throws UnknownHostException {

        operationDataTraceRecordHooks.stream()
                .filter(s -> s.isSupport(target))
                .findFirst()
                .ifPresent(r -> r.preCreateOperationDataTraceRecord(type, target, data));

        OperationDataTraceMetadata metadata = new OperationDataTraceMetadata();
        metadata.setType(type);
        metadata.setTarget(target);
        metadata.setData(data);

        OperationDataTraceRecord record = new OperationDataTraceRecord();
        record.setPrincipal(InetAddress.getLocalHost().getHostAddress());
        record.setData(metadata);

        operationDataTraceRecordHooks.stream()
                .filter(s -> s.isSupport(target))
                .findFirst()
                .ifPresent(r -> r.postCreateOperationDataTraceRecord(record));

        return record;
    }

    /**
     * 获取操作数据追踪配置属性
     *
     * @return 操作数据追踪配置属性
     */
    public OperationDataTraceProperties getOperationDataTraceProperties() {
        return operationDataTraceProperties;
    }

    /**
     * 获取存储定位生成器
     *
     * @return 存储定位生成器
     */
    public StoragePositioningGenerator getStoragePositioningGenerator() {
        return storagePositioningGenerator;
    }

    /**
     * 设置存储定位生成器
     *
     * @param storagePositioningGenerator 存储定位生成器
     */
    public void setStoragePositioningGenerator(StoragePositioningGenerator storagePositioningGenerator) {
        this.storagePositioningGenerator = storagePositioningGenerator;
    }

    @Override
    public List<OperationDataTraceRecordHook> getOperationDataTraceRecordHooks() {
        return operationDataTraceRecordHooks;
    }
}
