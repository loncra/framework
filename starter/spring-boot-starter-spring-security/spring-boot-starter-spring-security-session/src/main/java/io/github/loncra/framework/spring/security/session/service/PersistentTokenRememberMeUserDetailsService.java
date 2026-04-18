package io.github.loncra.framework.spring.security.session.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.LinkedHashSet;
import java.util.UUID;

/**
 * 持久化 token 的记住我用户明细服务实现,用于完成原生记住我且根据用户类型构造 {@link UserDetails } 使用
 *
 * @author maurice.chen
 */
public class PersistentTokenRememberMeUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, UUID.randomUUID().toString(), new LinkedHashSet<>());
    }

}
