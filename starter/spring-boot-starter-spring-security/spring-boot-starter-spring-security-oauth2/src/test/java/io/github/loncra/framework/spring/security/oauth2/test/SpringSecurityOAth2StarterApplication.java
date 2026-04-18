package io.github.loncra.framework.spring.security.oauth2.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@SpringBootApplication(
        scanBasePackages = "io.github.loncra.framework.spring.security.oauth2.test"
)
public class SpringSecurityOAth2StarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityOAth2StarterApplication.class, args);
    }

}
