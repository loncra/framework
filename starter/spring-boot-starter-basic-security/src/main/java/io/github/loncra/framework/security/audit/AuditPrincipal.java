package io.github.loncra.framework.security.audit;

/**
 * 审计当事人信息接口
 *
 * @author maurice.chen
 */
public interface AuditPrincipal {

    /**
     * 获取操作人
     *
     * @return 操作人
     */
    String getPrincipal();

    /**
     * 设置操作人
     *
     * @param principal 操作人
     */
    void setPrincipal(String principal);

}
