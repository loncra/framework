package io.github.loncra.framework.spring.security.core.authentication.adapter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * web security config 的后续适配器，用于在构造好 spring security 后的自定义扩展使用
 *
 * @author maurice.chen
 */
public interface WebSecurityConfigurerAfterAdapter {

    /**
     * 配置 http 访问安全
     *
     * @param httpSecurity http 访问安全
     */
    default void configure(HttpSecurity httpSecurity) throws Exception {

    }

    /**
     * 配置 web 安全
     *
     * @param web web 安全
     */
    default void configure(WebSecurity web) {

    }

    /**
     * 执行 httpSecurity.build() 后出发此方法
     *
     * @param securityFilterChain spring security 实例
     */
    default void build(SecurityFilterChain securityFilterChain, HttpSecurity httpSecurity) {

    }
}
