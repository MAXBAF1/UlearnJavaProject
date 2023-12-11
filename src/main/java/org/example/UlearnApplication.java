package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class UlearnApplication {
    public static void main(String[] args) {
        var builder = new SpringApplicationBuilder(UlearnApplication.class);
        builder.headless(false);
        builder.run(args);
    }
}
