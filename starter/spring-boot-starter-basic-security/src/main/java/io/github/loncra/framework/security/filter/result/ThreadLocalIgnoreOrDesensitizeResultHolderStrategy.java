package io.github.loncra.framework.security.filter.result;

import java.util.List;
import java.util.Objects;

/**
 * Thread Local 的 socket 结果集持有者实现
 *
 * @author maurice.chen
 */
public class ThreadLocalIgnoreOrDesensitizeResultHolderStrategy implements IgnoreOrDesensitizeResultHolderStrategy {

    private static final ThreadLocal<List<String>> IGNORE_PROPERTIES_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<List<String>> DESENSITIZE_PROPERTIES_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void clear() {
        IGNORE_PROPERTIES_THREAD_LOCAL.remove();
        DESENSITIZE_PROPERTIES_THREAD_LOCAL.remove();
    }

    @Override
    public List<String> getIgnoreProperties() {
        return IGNORE_PROPERTIES_THREAD_LOCAL.get();
    }

    @Override
    public void setIgnoreProperties(List<String> properties) {
        Objects.requireNonNull(properties, "properties 不能为空");
        IGNORE_PROPERTIES_THREAD_LOCAL.set(properties);
    }

    @Override
    public List<String> getDesensitizeProperties() {
        return DESENSITIZE_PROPERTIES_THREAD_LOCAL.get();
    }

    @Override
    public void setDesensitizeProperties(List<String> properties) {
        Objects.requireNonNull(properties, "properties 不能为空");
        DESENSITIZE_PROPERTIES_THREAD_LOCAL.set(properties);
    }

}
