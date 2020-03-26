package com.pmo.demo.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = { "com.pmo.demo.admin" })
public class DemoAdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DemoAdminApplication.class, args);
    }
}
