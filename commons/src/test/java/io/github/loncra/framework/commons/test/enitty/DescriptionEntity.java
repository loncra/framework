package io.github.loncra.framework.commons.test.enitty;

import io.github.loncra.framework.commons.annotation.Description;
import io.github.loncra.framework.commons.domain.AckMessage;
import io.github.loncra.framework.commons.domain.metadata.ProtocolMetadata;
import io.github.loncra.framework.commons.domain.metadata.RefreshAccessTokenMetadata;

import java.util.List;

@Description("描述数据")
public class DescriptionEntity {

    @Description("键值对数据")
    private NamValueEntity namValueEntity;

    @Description("协议元数据")
    private ProtocolMetadata protocolMetadata;

    @Description("值")
    String value;

    @Description("访问令牌元数据")
    private RefreshAccessTokenMetadata accessTokenMetadata;

    @Description("应答消息")
    private List<AckMessage> ackMessages;

    @Description("键值对数据")
    private List<NamValueEntity> namValueEntities;

    public DescriptionEntity() {
    }

    public NamValueEntity getNamValueEntity() {
        return namValueEntity;
    }

    public void setNamValueEntity(NamValueEntity namValueEntity) {
        this.namValueEntity = namValueEntity;
    }

    public ProtocolMetadata getProtocolMetadata() {
        return protocolMetadata;
    }

    public void setProtocolMetadata(ProtocolMetadata protocolMetadata) {
        this.protocolMetadata = protocolMetadata;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public RefreshAccessTokenMetadata getAccessTokenMetadata() {
        return accessTokenMetadata;
    }

    public void setAccessTokenMetadata(RefreshAccessTokenMetadata accessTokenMetadata) {
        this.accessTokenMetadata = accessTokenMetadata;
    }
}
