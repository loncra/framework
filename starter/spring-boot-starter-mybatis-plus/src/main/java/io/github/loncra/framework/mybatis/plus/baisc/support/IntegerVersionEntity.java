package io.github.loncra.framework.mybatis.plus.baisc.support;

import com.baomidou.mybatisplus.annotation.Version;
import io.github.loncra.framework.commons.id.number.IntegerIdEntity;
import io.github.loncra.framework.mybatis.plus.baisc.VersionEntity;


/**
 * 整形，且带版本号的实体基类
 *
 * @param <V> 版本号类型
 *
 * @author maurice.chen
 */
public class IntegerVersionEntity<V> extends IntegerIdEntity implements VersionEntity<V, Integer> {

    @Version
    private V version;

    @Override
    public void setVersion(V version) {
        this.version = version;
    }

    @Override
    public V getVersion() {
        return version;
    }
}
