package io.github.loncra.framework.spring.security.core.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.loncra.framework.spring.security.core.test.entity.SecurityTenantResourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SecurityTenantResourceDao extends BaseMapper<SecurityTenantResourceEntity> {
}
