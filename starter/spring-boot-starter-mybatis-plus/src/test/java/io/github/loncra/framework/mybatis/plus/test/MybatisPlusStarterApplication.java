package io.github.loncra.framework.mybatis.plus.test;

import io.github.loncra.framework.crypto.algorithm.cipher.AesCipherService;
import io.github.loncra.framework.crypto.algorithm.cipher.OperationMode;
import io.github.loncra.framework.mybatis.plus.crypto.DataAesCryptoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MybatisPlusStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusStarterApplication.class, args);
    }

    @Bean("aesEcbCryptoService")
    public DataAesCryptoService aesEcbCryptoService() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setInitializationVectorSize(0);
        aesCipherService.setMode(OperationMode.ECB);
        aesCipherService.setRandomNumberGenerator(null);
        return new DataAesCryptoService(aesCipherService, "jmUFt7sqMPXf+c8w69OpIg==");
    }

    @Bean
    public AesCipherService getEcbPkcs5AesCipherService() {

        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setInitializationVectorSize(0);
        aesCipherService.setMode(OperationMode.ECB);
        aesCipherService.setRandomNumberGenerator(null);

        return aesCipherService;
    }

}
