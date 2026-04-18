package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameEnum;

/**
 * 文件传输类型
 *
 * @author maurice.chen
 */
public enum UploadFileTransportTypeEnum implements NameEnum {
    /**
     * 平台商户业务订单明细文件
     */
    SFTP("平台商户业务订单明细文件"),
    /**
     * 通用渠道支付对账明细文件
     */
    OSS("通用渠道支付对账明细文件"),
    /**
     * 平台商户分账明细文件
     */
    MSG("平台商户分账明细文件"),

    ;

    UploadFileTransportTypeEnum(String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public String getName() {
        return name;
    }
}
