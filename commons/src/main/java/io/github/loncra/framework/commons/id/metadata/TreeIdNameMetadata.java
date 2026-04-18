package io.github.loncra.framework.commons.id.metadata;

import io.github.loncra.framework.commons.tree.Tree;

import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 带 id 的树形名称元数据
 *
 * @author maurice.chen
 */
public class TreeIdNameMetadata extends IdNameMetadata implements Tree<String, TreeIdNameMetadata> {

    @Serial
    private static final long serialVersionUID = -47608864850154234L;

    /**
     * 元数据信息
     */
    private Map<String, Object> metadata = new LinkedHashMap<>();

    /**
     * 子节点列表
     */
    private List<Tree<String, TreeIdNameMetadata>> children = new LinkedList<>();

    /**
     * 父节点 id
     */
    private String parent;

    /**
     * 创建带 id 的树形名称元数据
     *
     * @param id     id 值
     * @param name   名称
     * @param parent 父节点 id
     *
     * @return 带 id 的树形名称元数据
     */
    public static TreeIdNameMetadata of(
            String id,
            String name,
            String parent
    ) {
        TreeIdNameMetadata tree = new TreeIdNameMetadata();

        tree.setParent(parent);
        tree.setId(id);
        tree.setName(name);

        return tree;
    }

    /**
     * 获取元数据信息
     *
     * @return 元数据信息
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * 设置元数据信息
     *
     * @param metadata 元数据信息
     */
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    /**
     * 获取子节点列表
     *
     * @return 子节点列表
     */
    @Override
    public List<Tree<String, TreeIdNameMetadata>> getChildren() {
        return children;
    }

    /**
     * 设置子节点列表
     *
     * @param children 子节点列表
     */
    public void setChildren(List<Tree<String, TreeIdNameMetadata>> children) {
        this.children = children;
    }

    /**
     * 获取父节点 id
     *
     * @return 父节点 id
     */
    @Override
    public String getParent() {
        return parent;
    }

    /**
     * 设置父节点 id
     *
     * @param parent 父节点 id
     */
    public void setParent(String parent) {
        this.parent = parent;
    }
}
