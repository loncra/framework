package io.github.loncra.framework.spring.security.core.test.service;

import io.github.loncra.framework.mybatis.plus.service.BasicService;
import io.github.loncra.framework.spring.security.core.test.dao.SecurityTenantResourceDao;
import io.github.loncra.framework.spring.security.core.test.entity.SecurityTenantResourceEntity;
import org.springframework.stereotype.Service;

@Service
public class SecurityTenantResourceService extends BasicService<SecurityTenantResourceDao, SecurityTenantResourceEntity> {
}
