package io.github.loncra.framework.spring.security.core.test.service;

import io.github.loncra.framework.mybatis.plus.service.BasicService;
import io.github.loncra.framework.spring.security.core.test.dao.OperationDataDao;
import io.github.loncra.framework.spring.security.core.test.entity.OperationDataEntity;
import org.springframework.stereotype.Service;

@Service
public class OperationDataService extends BasicService<OperationDataDao, OperationDataEntity> {
}
