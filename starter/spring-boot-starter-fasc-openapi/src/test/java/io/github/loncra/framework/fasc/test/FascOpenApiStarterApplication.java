package io.github.loncra.framework.fasc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "io.github.loncra.framework.fasc")
public class FascOpenApiStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FascOpenApiStarterApplication.class, args);
    }

}
