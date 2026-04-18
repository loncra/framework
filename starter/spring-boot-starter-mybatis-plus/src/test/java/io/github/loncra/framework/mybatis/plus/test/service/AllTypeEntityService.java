package io.github.loncra.framework.mybatis.plus.test.service;

import io.github.loncra.framework.mybatis.plus.service.BasicService;
import io.github.loncra.framework.mybatis.plus.test.entity.AllTypeEntity;
import io.github.loncra.framework.mybatis.plus.test.mapper.AllTypeEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AllTypeEntityService extends BasicService<AllTypeEntityMapper, AllTypeEntity> {
}
