package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author YangWenjun
 * @date 2019/10/30 11:38
 * @project MockFramework
 * @title: SpringBootApplication
 * @description:
 */
//注釋掉的地方就是打war包时需要继承SpringBootServletInitializer，并重写configure方法
    @org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {"com"},exclude = SecurityAutoConfiguration.class)
    public class SpringBootApplication /*extends SpringBootServletInitializer*/ {

        public static void main(String[] args) {
            SpringApplication.run(SpringBootApplication.class, args);
        }
   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringBootApplication.class);
    }*/
    }
