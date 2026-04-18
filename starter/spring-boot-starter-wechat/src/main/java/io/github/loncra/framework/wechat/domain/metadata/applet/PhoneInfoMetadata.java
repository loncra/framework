package io.github.loncra.framework.wechat.domain.metadata.applet;

import io.github.loncra.framework.commons.CastUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 手机号码信息元数据
 *
 * @author maurice.chen
 */
public class PhoneInfoMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = 3924633879639719980L;

    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;

    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;

    /**
     * 区号
     */
    private String countryCode;

    /**
     * 数据水印
     */
    private WatermarkMetadata watermark;

    /**
     * 构造函数
     *
     * @param body 响应体数据
     */
    public PhoneInfoMetadata(Map<String, Object> body) {
        this.phoneNumber = body.get("phoneNumber").toString();
        this.purePhoneNumber = body.get("purePhoneNumber").toString();
        this.countryCode = body.get("countryCode").toString();
        Map<String, Object> watermarkData = CastUtils.cast(body.get("watermark"));
        if (MapUtils.isNotEmpty(watermarkData)) {
            this.watermark = new WatermarkMetadata();
            watermark.setTimestamp(new Date(CastUtils.cast(watermarkData.get("timestamp"), Long.class)).toInstant());
            watermark.setAppId(watermarkData.get("appid").toString());
        }
    }

    /**
     * 获取用户绑定的手机号（国外手机号会有区号）
     *
     * @return 手机号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置用户绑定的手机号（国外手机号会有区号）
     *
     * @param phoneNumber 手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取没有区号的手机号
     *
     * @return 没有区号的手机号
     */
    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    /**
     * 设置没有区号的手机号
     *
     * @param purePhoneNumber 没有区号的手机号
     */
    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    /**
     * 获取区号
     *
     * @return 区号
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 设置区号
     *
     * @param countryCode 区号
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 获取数据水印
     *
     * @return 数据水印
     */
    public WatermarkMetadata getWatermark() {
        return watermark;
    }

    /**
     * 设置数据水印
     *
     * @param watermark 数据水印
     */
    public void setWatermark(WatermarkMetadata watermark) {
        this.watermark = watermark;
    }
}
