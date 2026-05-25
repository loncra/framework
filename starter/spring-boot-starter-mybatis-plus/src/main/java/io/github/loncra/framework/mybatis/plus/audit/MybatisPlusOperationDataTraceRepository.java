package io.github.loncra.framework.mybatis.plus.audit;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.HttpRequestParameterMapUtils;
import io.github.loncra.framework.commons.ObjectUtils;
import io.github.loncra.framework.commons.id.BasicIdentification;
import io.github.loncra.framework.commons.id.IdEntity;
import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.enumerate.OperationDataType;
import io.github.loncra.framework.mybatis.interceptor.audit.AbstractOperationDataTraceRepository;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecordHook;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRepository;
import io.github.loncra.framework.security.audit.IdAuditEvent;
import io.github.loncra.framework.security.audit.IdStoragePositioningAuditEvent;
import io.github.loncra.framework.security.audit.StoragePositioningAuditEvent;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.net.UnknownHostException;
import java.util.*;

/**
 * mybatis-plus 操作数据留痕仓库：在 {@link AbstractOperationDataTraceRepository} 基础上解析实体 ID、Wrapper 条件，
 * 将 {@link OperationDataTraceRecord} 转为 {@link org.springframework.boot.actuate.audit.AuditEvent} 并通过 {@link org.springframework.context.ApplicationEventPublisher} 发布。
 * <p>带 {@code storagePositioning} 的记录包装为 {@link io.github.loncra.framework.security.audit.StoragePositioningAuditEvent}；
 * 实体 ID 场景使用 {@link EntityIdOperationDataTraceMetadata}（{@link io.github.loncra.framework.commons.CastUtils#of} 从 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata} 拷贝并 {@code setId}）。</p>
 *
 * @author maurice.chen
 * @see io.github.loncra.framework.spring.security.core.audit.SecurityPrincipalOperationDataTraceRepository
 */
public class MybatisPlusOperationDataTraceRepository extends AbstractOperationDataTraceRepository implements OperationDataTraceRepository, ApplicationEventPublisherAware {

    /**
     * WHERE 条件分隔符正则表达式
     */
    public static final String WHERE_SEPARATE = "\\s+(?i:and|or)\\s+";

    /**
     * Spring 应用事件发布器
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 创建一个 Mybatis-Plus 操作数据留痕仓库
     *
     * @param operationDataTraceProperties 操作数据追踪配置属性
     * @param operationDataTraceRecordHooks 留痕记录 Hook 集合
     */
    public MybatisPlusOperationDataTraceRepository(
            OperationDataTraceProperties operationDataTraceProperties,
            List<OperationDataTraceRecordHook> operationDataTraceRecordHooks
    ) {
        super(operationDataTraceProperties, operationDataTraceRecordHooks);
    }

    /**
     * 创建一个 Mybatis-Plus 操作数据留痕仓库（无 Hook）
     *
     * @param operationDataTraceProperties 操作数据追踪配置属性
     * @param applicationEventPublisher    Spring 应用事件发布器
     */
    public MybatisPlusOperationDataTraceRepository(
            OperationDataTraceProperties operationDataTraceProperties,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        super(operationDataTraceProperties);
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 创建审计事件
     *
     * @param record 操作数据追踪记录
     *
     * @return 审计事件
     */
    public AuditEvent createAuditEvent(OperationDataTraceRecord record) {
        Map<String, Object> data = CastUtils.convertValue(record.getData(), CastUtils.MAP_TYPE_REFERENCE);
        return createAuditEvent(record, data);
    }

    @Override
    protected List<OperationDataTraceRecord> createInsertRecord(
            Insert insert,
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {
        if (parameter instanceof MapperMethod.ParamMap<?>) {
            MapperMethod.ParamMap<?> map = CastUtils.cast(parameter);
            Object entity = map.get(Constants.ENTITY);

            if (Objects.isNull(entity)) {
                return super.createInsertRecord(insert, mappedStatement, statement, parameter);
            }
            if (!BasicIdentification.class.isAssignableFrom(entity.getClass())) {
                return super.createInsertRecord(insert, mappedStatement, statement, parameter);
            }

            BasicIdentification<Object> basicIdentification = CastUtils.cast(entity);
            OperationDataTraceRecord record = createEntityIdOperationDataTraceRecord(
                    basicIdentification,
                    insert.getTable().getName(),
                    OperationDataType.INSERT,
                    parameter
            );
            return Collections.singletonList(record);
        }
        else if (BasicIdentification.class.isAssignableFrom(parameter.getClass())) {
            BasicIdentification<Object> basicIdentification = CastUtils.cast(parameter);
            OperationDataTraceRecord record = createEntityIdOperationDataTraceRecord(
                    basicIdentification,
                    insert.getTable().getName(),
                    OperationDataType.INSERT,
                    parameter
            );
            return Collections.singletonList(record);
        }

        return super.createInsertRecord(insert, mappedStatement, statement, parameter);
    }

    /**
     * 创建带实体 ID 的操作数据追踪记录
     *
     * @param basicIdentification 基础标识对象
     * @param tableName           表名
     * @param type                操作数据类型
     *
     * @return 带实体 ID 的操作数据追踪记录
     *
     * @throws UnknownHostException 获取主机地址异常
     */
    private OperationDataTraceRecord createEntityIdOperationDataTraceRecord(
            BasicIdentification<Object> basicIdentification,
            String tableName,
            OperationDataType type,
            Object parameter
    ) throws UnknownHostException {
        OperationDataTraceRecord result = super.createBasicOperationDataTraceRecord(
                type,
                tableName,
                CastUtils.convertValue(parameter, CastUtils.MAP_TYPE_REFERENCE)
        );
        EntityIdOperationDataTraceMetadata metadata = CastUtils.of(result.getData(), EntityIdOperationDataTraceMetadata.class);
        metadata.setId(basicIdentification.getId());
        result.setData(metadata);
        return result;
    }

    @Override
    protected List<OperationDataTraceRecord> createUpdateRecord(
            Update update,
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {
        return createUpdateOrDeleteRecord(update.getTable().getName(), OperationDataType.UPDATE, parameter);
    }

    /**
     * 获取更新修改的字段映射
     *
     * @param slqSet          SQL SET 语句
     * @param parameterObject 参数对象
     *
     * @return 字段映射
     *
     * @throws OgnlException OGNL 表达式异常
     */
    private Map<String, Object> getUpdateModifiedMap(
            String slqSet,
            Object parameterObject
    ) throws OgnlException {
        String[] fields = StringUtils.splitByWholeSeparator(slqSet, CastUtils.COMMA);
        Map<String, Object> result = new LinkedHashMap<>();
        for (String field : fields) {
            String name = StringUtils.substringBefore(field, HttpRequestParameterMapUtils.EQ);
            String exp = StringUtils.substringAfter(field, HttpRequestParameterMapUtils.EQ);
            Object value = getOgnlValue(exp, parameterObject);
            result.put(name, value);
        }

        return Collections.unmodifiableMap(result);
    }

    @Override
    public void saveOperationDataTraceRecord(List<OperationDataTraceRecord> records) throws Exception {
        String uuid = UUID.randomUUID().toString();
        for (OperationDataTraceRecord record : records) {
            AuditEvent auditEvent = createAuditEvent(record);
            if (Objects.isNull(auditEvent)) {
                continue;
            }

            AuditEvent publishEvent;
            if (auditEvent instanceof StoragePositioningAuditEvent storagePositioningAuditEvent) {
                publishEvent = new IdStoragePositioningAuditEvent(uuid, storagePositioningAuditEvent);
            }
            else {
                publishEvent = new IdAuditEvent(uuid, auditEvent);
            }

            applicationEventPublisher.publishEvent(new AuditApplicationEvent(publishEvent));
        }
    }

    /**
     * 创建审计事件
     *
     * @param record 操作数据追踪记录
     * @param data   数据映射
     *
     * @return 审计事件
     */
    public AuditEvent createAuditEvent(
            OperationDataTraceRecord record,
            Map<String, Object> data
    ) {
        String type = getOperationDataTraceProperties().getAuditPrefixName()
                + CastUtils.UNDERSCORE
                + record.getData().getTarget()
                + CastUtils.UNDERSCORE
                + record.getData().getType().getValue();
        return createAuditEvent(record, type, data);
    }

    public AuditEvent createAuditEvent(
            OperationDataTraceRecord record,
            String auditType,
            Map<String, Object> data
    ) {
        if (StringUtils.isNotEmpty(record.getStoragePositioning())) {
            return new StoragePositioningAuditEvent(
                    record.getStoragePositioning(),
                    record.getCreationTime(),
                    record.getPrincipal().toString(),
                    auditType,
                    data
            );
        }
        return new AuditEvent(
                record.getCreationTime(),
                record.getPrincipal().toString(),
                auditType,
                data
        );
    }

    /**
     * 从 SQL 片段中获取 ID 值表达式
     *
     * @param sqlSegment      SQL 片段
     * @param parameterObject 参数对象
     *
     * @return ID 值
     *
     * @throws OgnlException OGNL 表达式异常
     */
    private Object getIdValueExp(
            String sqlSegment,
            Object parameterObject
    ) throws OgnlException {
        List<String> conditions = Arrays.asList(StringUtils.substringsBetween(sqlSegment, StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET));
        List<String> fields = conditions.stream().flatMap(s -> Arrays.stream(s.split(WHERE_SEPARATE))).toList();

        Object idValue = null;
        for (String field : fields) {
            String name = StringUtils.trim(StringUtils.substringBefore(field, HttpRequestParameterMapUtils.EQ));
            if (!IdEntity.ID_FIELD_NAME.equals(name)) {
                continue;
            }
            String exp = StringUtils.trim(StringUtils.substringAfter(field, HttpRequestParameterMapUtils.EQ));
            idValue = getOgnlValue(exp, parameterObject);
        }

        return idValue;
    }

    /**
     * 通过 OGNL 表达式获取值
     *
     * @param exp             OGNL 表达式
     * @param parameterObject 参数对象
     *
     * @return 表达式值
     *
     * @throws OgnlException OGNL 表达式异常
     */
    private Object getOgnlValue(
            String exp,
            Object parameterObject
    ) throws OgnlException {
        Object value = Ognl.getValue(exp, parameterObject);
        if (Map.class.isAssignableFrom(value.getClass())) {
            Map<Object, Object> mapValue = CastUtils.cast(value);
            return mapValue.keySet().iterator().next();
        }

        return value;
    }

    /**
     * 创建更新或删除记录
     *
     * @param tableName 表名
     * @param type      操作数据类型
     * @param parameter 参数对象
     *
     * @return 操作数据追踪记录列表
     *
     * @throws Exception 创建异常
     */
    protected List<OperationDataTraceRecord> createUpdateOrDeleteRecord(
            String tableName,
            OperationDataType type,
            Object parameter
    ) throws Exception {
        if (parameter instanceof MapperMethod.ParamMap<?>) {
            MapperMethod.ParamMap<?> map = CastUtils.cast(parameter);

            Object wrapper = null;
            if (map.containsKey(Constants.WRAPPER)) {
                wrapper = map.get(Constants.WRAPPER);
            }

            Object entity = null;
            if (map.containsKey(Constants.ENTITY)) {
                entity = map.get(Constants.ENTITY);
            }

            if (Objects.isNull(entity) && Objects.isNull(wrapper)) {
                Map<String, Object> submitData = CastUtils.convertValue(parameter, CastUtils.MAP_TYPE_REFERENCE);
                return Collections.singletonList(createBasicOperationDataTraceRecord(type, tableName, submitData));
            }

            if (Objects.nonNull(entity) && BasicIdentification.class.isAssignableFrom(entity.getClass())) {
                BasicIdentification<Object> basicIdentification = CastUtils.cast(entity);
                OperationDataTraceRecord entityRecord = createEntityIdOperationDataTraceRecord(
                        basicIdentification,
                        tableName,
                        type,
                        CastUtils.convertValue(entity, CastUtils.MAP_TYPE_REFERENCE)
                );

                return Collections.singletonList(entityRecord);
            }

            if (Objects.nonNull(wrapper) && Wrapper.class.isAssignableFrom(wrapper.getClass())) {

                Wrapper<?> updateWrapper = CastUtils.cast(wrapper);
                String sqlSegment = updateWrapper.getSqlSegment();
                Object entityId = getIdValueExp(sqlSegment, map);

                if (Objects.nonNull(entityId)) {

                    OperationDataTraceRecord record = super.createBasicOperationDataTraceRecord(
                            type,
                            tableName,
                            CastUtils.convertValue(parameter, CastUtils.MAP_TYPE_REFERENCE)
                    );

                    EntityIdOperationDataTraceMetadata metadata = CastUtils.of(record.getData(), EntityIdOperationDataTraceMetadata.class);
                    metadata.setId(entityId);
                    if (OperationDataType.UPDATE.equals(type)) {
                        Map<String, Object> modifiedMap = getUpdateModifiedMap(updateWrapper.getSqlSet(), parameter);
                        metadata.setData(modifiedMap);
                    }
                    record.setData(metadata);
                    return Collections.singletonList(record);
                }
            }
        }
        else if (BasicIdentification.class.isAssignableFrom(parameter.getClass())) {
            BasicIdentification<Object> basicIdentification = CastUtils.cast(parameter);
            if (Objects.nonNull(basicIdentification.getId())) {
                OperationDataTraceRecord record = createEntityIdOperationDataTraceRecord(
                        basicIdentification,
                        tableName,
                        type,
                        parameter
                );
                return Collections.singletonList(record);
            }
        }

        Map<String, Object> submitData = CastUtils.convertValue(parameter, CastUtils.MAP_TYPE_REFERENCE);
        return Collections.singletonList(createBasicOperationDataTraceRecord(type, tableName, submitData));

    }

    @Override
    protected List<OperationDataTraceRecord> createDeleteRecord(
            Delete delete,
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {
        if (ObjectUtils.isPrimitive(parameter.getClass())) {
            OperationDataTraceRecord record = createBasicOperationDataTraceRecord(
                    OperationDataType.DELETE,
                    delete.getTable().getName(),
                    new LinkedHashMap<>()
            );
            EntityIdOperationDataTraceMetadata metadata = CastUtils.of(record.getData(), EntityIdOperationDataTraceMetadata.class);
            metadata.setId(parameter);
            record.setData(metadata);
            return Collections.singletonList(record);
        }

        return createUpdateOrDeleteRecord(delete.getTable().getName(), OperationDataType.DELETE, parameter);

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 获取 Spring 应用事件发布器
     *
     * @return Spring 应用事件发布器
     */
    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }
}
