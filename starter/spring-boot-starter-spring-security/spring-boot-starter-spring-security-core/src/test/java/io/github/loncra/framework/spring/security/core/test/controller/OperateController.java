package io.github.loncra.framework.spring.security.core.test.controller;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.security.plugin.Plugin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Plugin(
        name = "OperateController",
        id = "operate",
        type = "menu",
        sources = "test"
)
@RequestMapping("operate")
public class OperateController {

    @GetMapping("isAuthenticated")
    @PreAuthorize("isAuthenticated()")
    public RestResult<?> isAuthenticated() {
        return RestResult.of("isAuthenticated");
    }

    @GetMapping("isFullyAuthenticated")
    @PreAuthorize("isFullyAuthenticated()")
    public RestResult<?> isFullyAuthenticated() {
        return RestResult.of("isFullyAuthenticated");
    }

    @GetMapping("permsOperate")
    @PreAuthorize("hasAuthority('perms[operate]')")
    public RestResult<?> permsOperate() {
        return RestResult.of("permsOperate");
    }

    @GetMapping("pluginTestPermsOperate")
    @Plugin(name = "test perms 操作信息", sources = "test")
    @PreAuthorize("hasAuthority('perms[operate]') and isFullyAuthenticated()")
    public RestResult<?> pluginTestPermsOperate() {
        return RestResult.of("pluginTestPermsOperate");
    }

    @PostMapping("pluginTestPermsPostAuditOperate")
    @PreAuthorize("hasAuthority('perms[operate]') and isFullyAuthenticated()")
    @Plugin(name = "pluginTestPermsGetAuditOperate", sources = "test", audit = true)
    public RestResult<?> pluginTestPermsGetAuditOperate(HttpServletRequest request) {
        return RestResult.ofSuccess("pluginTestPermsGetAuditOperate", request.getParameterMap());
    }

    @GetMapping("pluginAnyPermsOperate")
    @Plugin(name = "pluginAnyPermsOperate", sources = "any")
    @PreAuthorize("hasAuthority('perms[operate]') and isFullyAuthenticated()")
    public RestResult<?> pluginAnyPermsOperate() {
        return RestResult.of("pluginAnyPermsOperate");
    }

    @GetMapping("feignBasicAuthentication")
    @PreAuthorize("hasRole('FEIGN')")
    public RestResult<?> feignBasicAuthentication() {
        return RestResult.of("feignBasicAuthentication");
    }

    @GetMapping("simpleBasicAuthentication")
    @PreAuthorize("hasRole('SIMPLE')")
    public RestResult<?> simpleBasicAuthentication() {
        return RestResult.of("simpleBasicAuthentication");
    }
}
