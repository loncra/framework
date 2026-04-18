package io.github.loncra.framework.commons.domain.metadata;

import io.github.loncra.framework.commons.id.BasicIdentification;

import java.io.Serial;
import java.util.Map;

/**
 * 描述元数据
 *
 * @author maurice.chen
 */
public class DescriptionMetadata implements BasicIdentification<String> {

    @Serial
    private static final long serialVersionUID = -1943586461947712033L;

    private String id;

    private String name;

    private String type;

    private Map<String, Object> metadata;

    private String source;

    private int sort;

    public DescriptionMetadata() {
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
