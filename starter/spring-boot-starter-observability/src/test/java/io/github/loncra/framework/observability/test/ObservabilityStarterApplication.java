package io.github.loncra.framework.observability.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ObservabilityStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservabilityStarterApplication.class, args);
    }

    @RestController
    static class DemoController {

        @GetMapping("/demo/hello")
        public String hello() {
            return "hello";
        }
    }
}
