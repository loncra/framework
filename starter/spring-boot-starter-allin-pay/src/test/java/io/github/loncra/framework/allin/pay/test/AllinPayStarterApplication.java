package io.github.loncra.framework.allin.pay.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AllinPayStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllinPayStarterApplication.class, args);
    }

}
