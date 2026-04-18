package io.github.loncra.framework.captcha.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = "io.github.loncra.framework.captcha"
)
public class CaptchaStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaptchaStarterApplication.class, args);
    }

}
