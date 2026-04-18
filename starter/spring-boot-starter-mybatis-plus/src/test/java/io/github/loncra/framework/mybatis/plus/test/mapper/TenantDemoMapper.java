package io.github.loncra.framework.mybatis.plus.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.loncra.framework.mybatis.plus.test.entity.TenantDemoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantDemoMapper extends BaseMapper<TenantDemoEntity> {
}
