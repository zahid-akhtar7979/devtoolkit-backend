package com.devtoolkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DevToolkitApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevToolkitApplication.class, args);
    }

    // Removed the corsConfigurer bean to avoid CORS configuration conflicts
} 