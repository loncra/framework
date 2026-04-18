package io.github.loncra.framework.security.filter.result;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.NamingUtils;
import io.github.loncra.framework.security.WebProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 忽略属性或脱敏属性结果过滤器
 *
 * @author maurice.chen
 */
public class IgnoreOrDesensitizeResultFilter extends OncePerRequestFilter {

    /**
     * Web 配置属性
     */
    private final WebProperties webProperties;

    /**
     * 构造函数
     *
     * @param webProperties Web 配置属性
     */
    public IgnoreOrDesensitizeResultFilter(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String convert = request.getRequestURI().replace(AntPathMatcher.DEFAULT_PATH_SEPARATOR, CastUtils.UNDERSCORE);
            String target = NamingUtils.castSnakeCaseToCamelCase(Strings.CS.removeStart(convert, CastUtils.UNDERSCORE));

            List<String> ignoreProperties = webProperties
                    .getIgnoreResultMap()
                    .get(target);
            if (CollectionUtils.isNotEmpty(ignoreProperties)) {
                IgnoreOrDesensitizeResultHolder.setIgnoreProperties(ignoreProperties);
            }

            List<String> desensitizeProperties = webProperties
                    .getDesensitizeResultMap()
                    .get(target);
            if (CollectionUtils.isNotEmpty(desensitizeProperties)) {
                IgnoreOrDesensitizeResultHolder.setDesensitizeProperties(desensitizeProperties);
            }

            filterChain.doFilter(request, response);
        }
        finally {
            IgnoreOrDesensitizeResultHolder.clear();
        }
    }
}
