package io.github.loncra.framework.nacos.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NacosStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosStarterApplication.class, args);
    }

}
