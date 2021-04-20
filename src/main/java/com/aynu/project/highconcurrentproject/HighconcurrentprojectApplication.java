package com.aynu.project.highconcurrentproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;


@EnableCaching
@SpringBootApplication
@EnableScheduling
@MapperScan(value = "com.aynu.project.highconcurrentproject.mapper")
public class HighconcurrentprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighconcurrentprojectApplication.class, args);

    }

}
