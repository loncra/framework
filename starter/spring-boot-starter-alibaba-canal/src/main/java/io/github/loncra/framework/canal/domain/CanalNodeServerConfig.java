package io.github.loncra.framework.canal.domain;

import io.github.loncra.framework.commons.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.Serial;
import java.util.Properties;

/**
 * canal 节点服务 dto
 *
 * @author maurice.chen
 */
public class CanalNodeServerConfig extends CanalNodeServer {

    @Serial
    private static final long serialVersionUID = -5902050923310113485L;

    /**
     * 配置信息
     */
    private String content;

    /**
     * 配置内容 md5 值
     */
    private String contentMd5;


    /**
     * 配置内容
     *
     * @return 配置内容
     */
    public Properties properties() {

        if (StringUtils.isEmpty(content)) {
            return new Properties();
        }

        try {
            return PropertiesLoaderUtils.loadProperties(new ByteArrayResource(content.getBytes()));
        }
        catch (Exception e) {
            throw new SystemException("读取 canal 服务配置内容出现异常", e);
        }
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
