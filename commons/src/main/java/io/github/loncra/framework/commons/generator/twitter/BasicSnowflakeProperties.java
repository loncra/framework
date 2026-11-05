package io.github.loncra.framework.commons.generator.twitter;

public class BasicSnowflakeProperties {

    /**
     * 最小节点值
     */
    public final static int MIN_NUMBER = 0;

    /**
     * 最大节点值
     */
    public final static int MAX_NUMBER = 31;

    /**
     * 数据中心编号，最大支持数据中心节点数 0~31，一共 32 个
     */
    private long dataCenterId;
    /**
     * 服务编号（业务编号），共3位以字符串组成，000~999，共1000个
     */
    private String serviceId;

    public BasicSnowflakeProperties() {
    }

    public BasicSnowflakeProperties(String serviceId) {
        this(MIN_NUMBER,serviceId);
    }

    public BasicSnowflakeProperties(
            long dataCenterId,
            String serviceId
    ) {
        this.dataCenterId = dataCenterId;
        this.serviceId = serviceId;
    }

    /**
     * 获取数据中心编号，最大支持数据中心节点数0~31，一共32个
     *
     * @return 数据中心编号
     */
    public long getDataCenterId() {
        return dataCenterId;
    }

    /**
     * 设置数据中心编号
     *
     * @param dataCenterId 数据中心编号，最大支持数据中心节点数0~31，一共32个
     */
    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获取服务编号（业务编号），共3位以字符串组成，000~999，共1000个
     *
     * @return 服务编号（业务编号）
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * 设置服务编号（业务编号）
     *
     * @param serviceId 服务编号（业务编号），共3位以字符串组成，000~999，共1000个
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
