package io.github.loncra.framework.spring.security.session.token;

import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import java.util.function.Supplier;

/**
 * 访问令牌形式的安全上下文延迟实现
 *
 * @author mauric.chen
 *
 */
public class SessionAccessTokenDeferredSecurityContext implements DeferredSecurityContext {

    private final Supplier<SecurityContext> supplier;

    private final SecurityContextHolderStrategy strategy;

    private SecurityContext securityContext;

    private boolean missingContext;

    SessionAccessTokenDeferredSecurityContext(
            Supplier<SecurityContext> supplier,
            SecurityContextHolderStrategy strategy
    ) {
        this.supplier = supplier;
        this.strategy = strategy;
    }

    @Override
    public SecurityContext get() {
        init();
        return this.securityContext;
    }

    @Override
    public boolean isGenerated() {
        init();
        return this.missingContext;
    }

    private void init() {
        if (this.securityContext != null) {
            return;
        }

        this.securityContext = this.supplier.get();
        this.missingContext = (this.securityContext == null);
        if (this.missingContext) {
            this.securityContext = this.strategy.createEmptyContext();
        }
    }
}
