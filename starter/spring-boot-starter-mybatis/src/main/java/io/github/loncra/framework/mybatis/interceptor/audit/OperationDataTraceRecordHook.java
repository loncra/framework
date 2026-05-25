package io.github.loncra.framework.mybatis.interceptor.audit;

import io.github.loncra.framework.mybatis.enumerate.OperationDataType;

import java.util.Map;

/**
 * 写库留痕链路中对 {@link OperationDataTraceRecord} 的扩展钩子，分三阶段触发。
 * <p><b>1. 构建阶段</b>（在 {@link AbstractOperationDataTraceRepository#createBasicOperationDataTraceRecord} 内）</p>
 * <ul>
 *   <li>{@link #preCreateOperationDataTraceRecord}：组装记录前，可就地修改即将写入 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata#getData()} 的 Map。</li>
 *   <li>{@link #postCreateOperationDataTraceRecord}：默认 {@code principal} 等填完后、进入 storagePosition 复制与 {@code save} 之前，可继续改 {@code record}（含 {@code record.getData()}）。</li>
 *   <li>以上两步均对已注入的 Hook 列表按顺序 {@code filter(isSupport).findFirst()}，<b>每个方法各自最多执行一个</b>匹配的 Hook（与表名互斥或配合 {@code @Order}）。</li>
 * </ul>
 * <p><b>2. 保存前阶段</b>（在 {@link OperationDataTraceInterceptor} 中，{@link OperationDataTraceRepository#createOperationDataTraceRecord} 返回非空之后、
 * {@link OperationDataTraceRepository#saveOperationDataTraceRecord} 之前）</p>
 * <ul>
 *   <li>先按 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata#getTarget()}（表名）分组，对<b>每组内每一条</b>记录，对所有 {@code isSupport(该表名)} 为 true 的 Hook <b>依次</b>调用
 *   {@link #preSaveOperationDataTraceRecord}（<b>多</b>个 Hook 可同时生效，与构建阶段的 {@code findFirst} 规则不同）。</li>
 *   <li>典型用途：落库/发审计事件前最后一轮补字段、裁剪、或依赖「完整记录列表」的场景（按表分组后逐条回调）。</li>
 * </ul>
 * <p><b>装配</b>：实现本接口并注册为 Spring Bean；
 * {@code spring-boot-starter-mybatis-plus} 与 {@code spring-boot-starter-spring-security-core} 在构造默认/安全增强的
 * {@link OperationDataTraceRepository} 时，通过 {@link org.springframework.beans.factory.ObjectProvider}{@code <OperationDataTraceRecordHook>}
 * 收集全部 Bean 并注入列表；{@link OperationDataTraceRepository#getOperationDataTraceRecordHooks()} 需返回同一列表供拦截器调用 {@code preSave}。</p>
 * <p><b>{@link #isSupport(String)}</b>：{@code name} 为 SQL 解析得到的<b>目标表名</b>（与 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata#getTarget()} 一致），
 * 大小写以 JSqlParser 解析结果为准。</p>
 * <p><b>remark</b>：框架不在 {@link AbstractOperationDataTraceRepository} 内自动填充 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata#getRemark()}；
 * 由控制器 {@code @Auditable} / {@code @OperationDataTrace} 或本 Hook 按需写入。</p>
 *
 * @author maurice.chen
 * @see AbstractOperationDataTraceRepository#createBasicOperationDataTraceRecord
 * @see OperationDataTraceInterceptor#intercept
 */
public interface OperationDataTraceRecordHook {

    /**
     * 是否参与指定目标表名的留痕记录构建与保存前回调。
     *
     * @param name 表名（target），与当前 INSERT/UPDATE/DELETE 语句中的表一致
     *
     * @return {@code true} 时该 Hook 可参与本表对应的 {@link #preCreateOperationDataTraceRecord}、{@link #postCreateOperationDataTraceRecord}
     * （受构建阶段 {@code findFirst} 约束）以及 {@link #preSaveOperationDataTraceRecord}（可与其它 Hook 叠加）
     */
    boolean isSupport(String name);

    /**
     * 在组装 {@link OperationDataTraceRecord} <b>之前</b>调用，可就地修改 {@code data}（例如脱敏、丢弃大字段、补全派生字段）。
     *
     * @param operationDataType 操作类型
     * @param name              表名
     * @param data              MyBatis 入参转成的 Map，与后续 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata#setData(Map)} 为同一引用
     */
    void preCreateOperationDataTraceRecord(
            OperationDataType operationDataType,
            String name,
            Map<String, Object> data
    );

    /**
     * 在 {@link OperationDataTraceRecord} 已设置 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata}（含 {@code target}、{@code type}、{@code data}）及默认 {@code principal}
     * <b>之后</b>调用，可继续修改记录对象（例如改写 {@code record.getData().setRemark(...)}、替换 {@code principal}）。
     *
     * @param record 即将进入 storagePosition 复制与后续 {@code save} 链路的记录
     */
    void postCreateOperationDataTraceRecord(OperationDataTraceRecord record);

    /**
     * 在拦截器即将调用 {@link OperationDataTraceRepository#saveOperationDataTraceRecord(java.util.List)} <b>之前</b>执行：
     * 此时库已写入成功，留痕记录列表已包含基础行及可选的 {@code storagePositioning} 复制行；可按表分组后对<b>每一条</b>记录做保存前处理。
     * <p>同一张表上所有 {@link #isSupport(String)} 为 true 的 Hook 都会收到回调，顺序为 {@link OperationDataTraceRepository#getOperationDataTraceRecordHooks()} 返回列表顺序；
     * 对每个 Hook，同组内每条记录各调用一次本方法。</p>
     *
     * @param operationDataTraceRecord 即将被持久化为审计事件/仓库的单条留痕记录
     */
    void preSaveOperationDataTraceRecord(OperationDataTraceRecord operationDataTraceRecord);
}
