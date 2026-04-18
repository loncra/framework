package io.github.loncra.framework.security.audit;

/**
 * 存储定位生成器
 *
 * @author maurice.chen
 */
public interface StoragePositioningGenerator {

    /**
     * 生成存储定位
     *
     * @param object 对象信息
     *
     * @return 存储定位
     */
    String generatePositioning(Object object);
}
