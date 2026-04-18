package io.github.loncra.framework.spring.security.core.entity;

/**
 * 带设备唯一识别的用户明细
 *
 * @author maurice.chen
 */
public interface DeviceIdentifiedUserDetails {

    /**
     * 获取设备唯一识别
     *
     * @return 设备唯一识别
     */
    String getDeviceIdentified();

    /**
     * 设置设备唯一识别
     *
     * @param deviceIdentified 设备唯一识别
     *
     */
    void setDeviceIdentified(String deviceIdentified);
}
