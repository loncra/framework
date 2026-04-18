package io.github.loncra.framework.spring.security.core.plugin;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 用户类型表决器实现，用于判断当前 controller 方法里面是否带有 {@link Plugin} 注解的记录是否符合当前用户调用
 *
 * @author maurice
 */
public class PluginSourceAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    private final AuthenticationProperties authenticationProperties;

    public PluginSourceAuthorizationManager(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {
        return authorize(authentication, object);
    }

    @Override
    public AuthorizationDecision authorize(Supplier<Authentication> supplier, MethodInvocation object) {

        Plugin plugin = AnnotationUtils.findAnnotation(object.getMethod(), Plugin.class);

        if (plugin == null) {
            return null;
        }

        Authentication authentication = supplier.get();

        if (!authentication.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        if (!AuditAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return null;
        }

        List<String> resourceTypes = Arrays
                .stream(plugin.sources())
                .filter(s -> !authenticationProperties.getPluginAuthorizationManagerSources().contains(s))
                .toList();

        if (CollectionUtils.isEmpty(resourceTypes)) {
            return new AuthorizationDecision(true);
        }

        AuditAuthenticationToken token = CastUtils.cast(authentication);

        if (!resourceTypes.contains(token.getType())) {
            return new AuthorizationDecision(false);
        } else {
            return new AuthorizationDecision(true);
        }
    }

}
