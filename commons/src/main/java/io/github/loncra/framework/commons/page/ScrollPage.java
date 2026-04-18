package io.github.loncra.framework.commons.page;


import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 滚动分页实体
 *
 * @author maurice.chen
 */
public class ScrollPage<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 9118490108501020682L;

    /**
     * 分页请求
     */
    protected ScrollPageRequest pageRequest;
    /**
     * 数据元素
     */
    private List<T> elements;
    /**
     * 元数据信息
     */
    private Map<String, Object> metadata = new LinkedHashMap<>();

    public ScrollPage() {
    }

    /**
     * 滚动分页实体
     *
     * @param pageRequest 分页请求
     * @param elements    数据元素
     */
    public ScrollPage(
            ScrollPageRequest pageRequest,
            List<T> elements
    ) {
        this.pageRequest = pageRequest;
        this.elements = elements;
    }

    /**
     * 获取每页的内容大小
     *
     * @return 内容数量
     */
    public int getSize() {
        return pageRequest == null ? 0 : pageRequest.getSize();
    }

    /**
     * 获取数据元素数量
     *
     * @return 数据元素数量
     */
    public int getNumberOfElements() {
        return elements.size();
    }

    /**
     * 判断是否存在下一页
     *
     * @return true 表示存在，否则 false
     */
    public boolean hasNext() {
        return getNumberOfElements() == getSize();
    }

    /**
     * 判断是否为尾页
     *
     * @return true 表示尾页，否则 false
     */
    public boolean isLast() {
        return !hasNext();
    }

    /**
     * 获取数据元素集合
     *
     * @return 当前页的分页数据集合
     */
    public List<T> getElements() {
        return Collections.unmodifiableList(elements);
    }

    /**
     * 设置数据元素集合
     *
     * @param elements 数据元素集合
     */
    public void setElements(List<T> elements) {
        this.elements = elements;
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
     * 设置分页请求
     *
     * @param pageRequest 分页请求
     */
    public void setPageRequest(ScrollPageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    /**
     * 获取分页请求
     *
     * @return 分页请求
     */
    public ScrollPageRequest getPageRequest() {
        return pageRequest;
    }

}
