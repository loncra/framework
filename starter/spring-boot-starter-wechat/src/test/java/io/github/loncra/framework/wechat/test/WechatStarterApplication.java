package io.github.loncra.framework.wechat.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.github.loncra.framework.wechat")
public class WechatStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatStarterApplication.class, args);
    }

}
