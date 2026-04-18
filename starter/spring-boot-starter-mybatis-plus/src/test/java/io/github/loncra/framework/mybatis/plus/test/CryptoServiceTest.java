package io.github.loncra.framework.mybatis.plus.test;

import io.github.loncra.framework.mybatis.plus.crypto.DataAesCryptoService;
import io.github.loncra.framework.mybatis.plus.test.entity.CryptoEntity;
import io.github.loncra.framework.mybatis.plus.test.service.CryptoEntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CryptoServiceTest {

    @Autowired
    private CryptoEntityService cryptoEntityService;

    @Autowired
    private DataAesCryptoService dataAesCryptoService;

    @Test
    public void testEncrypt() {
        CryptoEntity entity = new CryptoEntity();
        entity.setCryptoValue("18776974353");
        cryptoEntityService.save(entity);
        Assertions.assertEquals("18776974353", dataAesCryptoService.decrypt(entity.getCryptoValue()));

        entity = cryptoEntityService.lambdaQuery().eq(CryptoEntity::getCryptoValue, "18776974353").eq(CryptoEntity::getId, "1").one();
        Assertions.assertNotNull(entity);
        Assertions.assertEquals("18776974353", entity.getCryptoValue());
    }
}
