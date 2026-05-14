package io.github.loncra.framework.mybatis.resolver;

import io.github.loncra.framework.mybatis.enumerate.OperationDataType;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;

import java.util.Map;

/**
 * 操作数据留痕记录在「落库/发审计事件」之前的扩展点，挂载在
 * {@link io.github.loncra.framework.mybatis.interceptor.audit.AbstractOperationDataTraceResolver#createBasicOperationDataTraceRecord} 上。
 * <p>
 * 典型用途：按表名筛选后，在生成 {@link OperationDataTraceRecord} 之前清洗或补全 {@code submitData}；在生成之后调整
 * {@code remark}、{@code principal} 等字段。
 * </p>
 * <p>
 * <b>装配</b>：实现本接口并注册为 Spring Bean；{@code spring-boot-starter-mybatis-plus} 与
 * {@code spring-boot-starter-spring-security-core} 在构造默认/安全增强的
 * {@link io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceResolver} 时，会通过
 * {@link org.springframework.beans.factory.ObjectProvider} 收集全部 Bean，并按顺序注入列表。
 * </p>
 * <p>
 * <b>选取规则</b>：每条基础记录在 {@link #preCreateOperationDataTraceRecord} 与
 * {@link #postCreateOperationDataTraceRecord} 各执行一次解析；两次均对已注入列表顺序执行
 * {@code stream().filter(this::isSupport).findFirst()}，即同一表名若有多个实现支持，
 * <b>仅有顺序中的第一个</b>会生效（建议对表名范围互斥，或对实现类使用 {@code @Order} 明确优先级）。
 * </p>
 * <p>
 * <b>{@link #isSupport(String)}</b>：{@code name} 为 SQL 解析得到的<b>目标表名</b>（与 {@link OperationDataTraceRecord#getTarget()} 一致），
 * 大小写以 JSqlParser 解析结果为准。
 * </p>
 *
 * @author maurice.chen
 */
public interface OperationDataTraceRecordResolver {

    /**
     * 是否参与指定目标表名的留痕记录构建。
     *
     * @param name 表名（target），与当前 INSERT/UPDATE/DELETE 语句中的表一致
     *
     * @return {@code true} 时该 Bean 可能成为本条记录的前后置扩展（仍受列表顺序与 {@code findFirst} 约束）
     */
    boolean isSupport(String name);

    /**
     * 在组装 {@link OperationDataTraceRecord} <b>之前</b>调用，可就地修改 {@code submitData}（例如脱敏、丢弃大字段、补全派生字段）。
     *
     * @param operationDataType 操作类型
     * @param name              表名
     * @param submitData        MyBatis 入参转成的 Map，与后续写入记录的 {@link OperationDataTraceRecord#getSubmitData()} 为同一引用
     */
    void preCreateOperationDataTraceRecord(
            OperationDataType operationDataType,
            String name,
            Map<String, Object> submitData
    );

    /**
     * 在 {@link OperationDataTraceRecord} 已设置 {@code type}、{@code target}、{@code submitData}、默认 {@code principal}、{@code remark} 等字段
     * <b>之后</b>调用，可继续修改记录对象（例如改写 {@code remark}、替换 {@code principal}）。
     *
     * @param record 即将进入存储定位复制与 {@code saveOperationDataTraceRecord} 链路的记录
     */
    void postCreateOperationDataTraceRecord(OperationDataTraceRecord record);
}
