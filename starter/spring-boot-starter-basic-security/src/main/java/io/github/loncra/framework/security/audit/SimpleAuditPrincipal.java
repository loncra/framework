package io.github.loncra.framework.security.audit;

/**
 * 简单的审计当事人信息接口实现
 *
 * @author maurice.chen
 */
public class SimpleAuditPrincipal implements AuditPrincipal {

    /**
     * 当事人信息
     */
    private String principal;

    /**
     * 构造函数
     */
    public SimpleAuditPrincipal() {
    }

    /**
     * 构造函数
     *
     * @param principal 当事人信息
     */
    public SimpleAuditPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
