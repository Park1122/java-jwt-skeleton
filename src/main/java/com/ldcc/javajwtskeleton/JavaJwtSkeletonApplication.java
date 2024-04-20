package com.ldcc.javajwtskeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JavaJwtSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaJwtSkeletonApplication.class, args);
    }

}
