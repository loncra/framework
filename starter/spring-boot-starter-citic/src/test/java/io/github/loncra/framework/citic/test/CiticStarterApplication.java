package io.github.loncra.framework.citic.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.github.loncra.framework.citic")
public class CiticStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CiticStarterApplication.class, args);
    }

}
