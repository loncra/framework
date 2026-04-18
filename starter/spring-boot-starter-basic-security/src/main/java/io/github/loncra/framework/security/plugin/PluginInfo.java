package io.github.loncra.framework.security.plugin;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.MetadataUtils;
import io.github.loncra.framework.commons.tree.Tree;
import io.github.loncra.framework.security.entity.ResourceAuthority;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 插件信息
 *
 * @author maurice.chen
 */
public class PluginInfo extends ResourceAuthority implements Tree<String, PluginInfo> {

    @Serial
    private static final long serialVersionUID = -6354440242310314107L;

    public static final String DEFAULT_TYPE_VALUE = "security";

    /**
     * 默认版本号字段名称
     */
    public static String DEFAULT_VERSION_NAME = "version";

    /**
     * 默认插件字段名称
     */
    public static String DEFAULT_ARTIFACT_ID_NAME = "artifact-id";

    /**
     * 默认组字段名称
     */
    public static String DEFAULT_GROUP_ID_NAME = "group-id";

    /**
     * 默认的子节点字段名
     */
    public static final String DEFAULT_CHILDREN_NAME = "children";

    /**
     * 默认的来源字段名
     */
    public static final String DEFAULT_SOURCES_NAME = "sources";

    /**
     * 顶级父类表示
     */
    public static final String DEFAULT_ROOT_PARENT_NAME = "root";

    /**
     * id
     */
    private String id;

    /**
     * 顺序值
     */
    private Integer sort = 0;

    /**
     * 类型
     */
    private String type;

    /**
     * 来源: 参考 {@link Plugin#sources()};
     */
    private List<String> sources;

    /**
     * 父类
     */
    private String parent;

    /**
     * 子节点
     */
    private List<Tree<String, PluginInfo>> children = new ArrayList<>();

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 元数据信息
     */
    private Map<String, String> metadata;

    /**
     * 备注
     */
    private String remark;

    /**
     * 插件信息
     */
    public PluginInfo() {

    }

    /**
     * 插件信息
     *
     * @param plugin 插件信息
     */
    public PluginInfo(Plugin plugin) {
        this(plugin, new ArrayList<>());
    }

    /**
     * 插件信息
     *
     * @param plugin   插件信息
     * @param children 子节点
     */
    public PluginInfo(
            Plugin plugin,
            List<Tree<String, PluginInfo>> children
    ) {
        this.setId(plugin.id());

        this.setParent(plugin.parent());
        this.setChildren(children);

        this.setName(plugin.name());
        this.setSort(plugin.sort());
        this.setType(plugin.type());
        this.setSources(Arrays.asList(plugin.sources()));
        this.setRemark(plugin.remark());

        if (ArrayUtils.isNotEmpty(plugin.authority())) {
            this.setAuthority(StringUtils.join(plugin.authority(), CastUtils.COMMA));
        }
        if (ArrayUtils.isNotEmpty(plugin.metadata())) {
            Map<String, String> metadata = MetadataUtils.toMap(plugin.metadata());
            this.setMetadata(metadata);
        }
    }

    /**
     * 获取 id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 设置父类 id
     *
     * @param parent 父类 id
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * 设置子节点
     *
     * @param children 子节点
     */
    public void setChildren(List<Tree<String, PluginInfo>> children) {
        this.children = children;
    }

    /**
     * 获取顺序值
     *
     * @return 顺序值
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置顺序值
     *
     * @param sort 顺序值
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }


    /**
     * 获取资源类型
     *
     * @return MENU.菜单类型, SECURITY.资源类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置资源类型
     *
     * @param type MENU.菜单类型, SECURITY.资源类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取来源集合
     *
     * @return 来源集合
     */
    public List<String> getSources() {
        return sources;
    }

    /**
     * 设置来源集合
     *
     * @param sources 来源集合
     */
    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * 设置应用名称
     *
     * @param applicationName 应用名称
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public List<Tree<String, PluginInfo>> getChildren() {
        return children;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public boolean isChildren(Tree<String, PluginInfo> parent) {
        PluginInfo pluginInfo = CastUtils.cast(parent);
        return Strings.CS.equals(pluginInfo.getId(), this.parent);
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
