package io.github.loncra.framework.spring.security.session.test.service;

import io.github.loncra.framework.mybatis.plus.service.BasicService;
import io.github.loncra.framework.spring.security.session.test.dao.OperationDataDao;
import io.github.loncra.framework.spring.security.session.test.entity.OperationDataEntity;
import org.springframework.stereotype.Service;

@Service
public class OperationDataService extends BasicService<OperationDataDao, OperationDataEntity> {
}
