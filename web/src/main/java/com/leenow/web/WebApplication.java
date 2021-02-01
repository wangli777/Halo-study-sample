package com.leenow.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableAsync 启用SpringBoot的异步方法执行功能
 * @EnableScheduling 启用SpringBoot的计划任务执行功能
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan("com.leenow")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
