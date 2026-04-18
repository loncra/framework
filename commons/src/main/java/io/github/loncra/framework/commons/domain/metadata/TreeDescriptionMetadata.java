package io.github.loncra.framework.commons.domain.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.tree.Tree;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.util.LinkedList;
import java.util.List;

/**
 * 树形描述元数据信息
 *
 * @author maurice.chen
 */
public class TreeDescriptionMetadata extends DescriptionMetadata implements Tree<String, TreeDescriptionMetadata> {

    @Serial
    private static final long serialVersionUID = -3234541732210322019L;

    private String parentId;


    private List<Tree<String, TreeDescriptionMetadata>> children = new LinkedList<>();

    public TreeDescriptionMetadata() {
    }

    public TreeDescriptionMetadata(
            String id,
            String name
    ) {
        this(id, name, new LinkedList<>());
    }

    public TreeDescriptionMetadata(
            String id,
            String name,
            List<Tree<String, TreeDescriptionMetadata>> children
    ) {
        setId(id);
        setName(name);
        this.children = children;
    }

    public String getUniqueValue() {
        return StringUtils.isNotEmpty(parentId) ? (parentId + CastUtils.DOT + getId()) : getId();
    }

    @Override
    public List<Tree<String, TreeDescriptionMetadata>> getChildren() {
        return children;
    }

    @Override
    @JsonIgnore
    public String getParent() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setChildren(List<Tree<String, TreeDescriptionMetadata>> children) {
        this.children = children;
    }

    public static TreeDescriptionMetadata of(
            String id,
            String name,
            List<Tree<String, TreeDescriptionMetadata>> children
    ) {
        return new TreeDescriptionMetadata(id, name, children);
    }

    public static TreeDescriptionMetadata of(
            String id,
            String name
    ) {
        return new TreeDescriptionMetadata(id, name);
    }
}
