package io.github.loncra.framework.mybatis.plus.test.entity;

import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.id.metadata.IdNameMetadata;

/**
 * 创作模式
 *
 * @author maurice.chen
 */
public enum CreativeModeEnum implements ValueEnum<IdNameMetadata> {

    /**
     * 多参生视频
     */
    MULTI_IMAGE_TO_VIDEO(IdNameMetadata.of("多参生视频","多图参考生成分镜视频")),
    /**
     * 图生视频
     */
    IMAGE_TO_VIDEO(IdNameMetadata.of("图生视频","基于生成或上传分镜图生成分镜视频"));

    CreativeModeEnum(IdNameMetadata value) {
        this.value = value;
    }

    private final IdNameMetadata value;

    @Override
    public IdNameMetadata getValue() {
        return value;
    }
}
