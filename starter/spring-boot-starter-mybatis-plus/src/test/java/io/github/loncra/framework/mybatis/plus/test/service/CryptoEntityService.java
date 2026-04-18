package io.github.loncra.framework.mybatis.plus.test.service;

import io.github.loncra.framework.mybatis.plus.service.BasicService;
import io.github.loncra.framework.mybatis.plus.test.entity.CryptoEntity;
import io.github.loncra.framework.mybatis.plus.test.mapper.CryptoEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CryptoEntityService extends BasicService<CryptoEntityMapper, CryptoEntity> {
}
