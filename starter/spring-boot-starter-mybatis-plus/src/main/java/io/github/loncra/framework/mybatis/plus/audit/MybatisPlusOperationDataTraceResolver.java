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
import io.github.loncra.framework.mybatis.interceptor.audit.AbstractOperationDataTraceResolver;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceResolver;
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
 * mybatis-plus 操作数据留痕仓库实现
 *
 * @author maurice.chen
 */
public class MybatisPlusOperationDataTraceResolver extends AbstractOperationDataTraceResolver implements OperationDataTraceResolver, ApplicationEventPublisherAware {

    /**
     * WHERE 条件分隔符正则表达式
     */
    public static final String WHERE_SEPARATE = "\\s+(?i:and|or)\\s+";

    /**
     * Spring 应用事件发布器
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 创建一个 Mybatis-Plus 操作数据追踪解析器
     *
     * @param operationDataTraceProperties 操作数据追踪配置属性
     */
    public MybatisPlusOperationDataTraceResolver(OperationDataTraceProperties operationDataTraceProperties) {
        super(operationDataTraceProperties);
    }

    /**
     * 创建一个 Mybatis-Plus 操作数据追踪解析器
     *
     * @param operationDataTraceProperties 操作数据追踪配置属性
     * @param applicationEventPublisher    Spring 应用事件发布器
     */
    public MybatisPlusOperationDataTraceResolver(
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
        Map<String, Object> data = new LinkedHashMap<>();
        data.put(OperationDataTraceRecord.SUBMIT_DATA_FIELD, record.getSubmitData());
        data.put(OperationDataTraceRecord.REMARK_FIELD, record.getRemark());
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
                    OperationDataType.INSERT
            );
            return Collections.singletonList(record);
        }
        else if (BasicIdentification.class.isAssignableFrom(parameter.getClass())) {
            BasicIdentification<Object> basicIdentification = CastUtils.cast(parameter);
            OperationDataTraceRecord record = createEntityIdOperationDataTraceRecord(
                    basicIdentification,
                    insert.getTable().getName(),
                    OperationDataType.INSERT
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
    private EntityIdOperationDataTraceRecord createEntityIdOperationDataTraceRecord(
            BasicIdentification<Object> basicIdentification,
            String tableName,
            OperationDataType type
    ) throws UnknownHostException {
        OperationDataTraceRecord result = super.createBasicOperationDataTraceRecord(
                type,
                tableName,
                new LinkedHashMap<>()
        );
        EntityIdOperationDataTraceRecord entityRecord = CastUtils.of(result, EntityIdOperationDataTraceRecord.class);
        entityRecord.setSubmitData(CastUtils.convertValue(basicIdentification, CastUtils.MAP_TYPE_REFERENCE));
        entityRecord.setEntityId(basicIdentification.getId());
        return entityRecord;
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
        if (StringUtils.isNotEmpty(record.getStoragePositioning())) {
            return new StoragePositioningAuditEvent(
                    record.getStoragePositioning(),
                    record.getCreationTime(),
                    record.getPrincipal().toString(),
                    getOperationDataTraceProperties().getAuditPrefixName() + CastUtils.UNDERSCORE + record.getTarget() + CastUtils.UNDERSCORE + record.getType(),
                    data
            );
        }
        return new AuditEvent(
                record.getCreationTime(),
                record.getPrincipal().toString(),
                getOperationDataTraceProperties().getAuditPrefixName() + CastUtils.UNDERSCORE + record.getTarget() + CastUtils.UNDERSCORE + record.getType(),
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
                EntityIdOperationDataTraceRecord entityRecord = createEntityIdOperationDataTraceRecord(
                        basicIdentification,
                        tableName,
                        type
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
                            new LinkedHashMap<>()
                    );

                    EntityIdOperationDataTraceRecord entityRecord = CastUtils.of(record, EntityIdOperationDataTraceRecord.class);

                    entityRecord.setEntityId(entityId);
                    if (OperationDataType.UPDATE.equals(type)) {
                        Map<String, Object> submitData = getUpdateModifiedMap(updateWrapper.getSqlSet(), parameter);
                        entityRecord.setSubmitData(submitData);
                    }
                    return Collections.singletonList(entityRecord);
                }
            }
        }
        else if (BasicIdentification.class.isAssignableFrom(parameter.getClass())) {
            BasicIdentification<Object> basicIdentification = CastUtils.cast(parameter);
            if (Objects.nonNull(basicIdentification.getId())) {
                OperationDataTraceRecord record = createEntityIdOperationDataTraceRecord(
                        basicIdentification,
                        tableName,
                        type
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
            EntityIdOperationDataTraceRecord entityRecord = CastUtils.of(record, EntityIdOperationDataTraceRecord.class);
            entityRecord.setEntityId(parameter);
            return Collections.singletonList(entityRecord);
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
