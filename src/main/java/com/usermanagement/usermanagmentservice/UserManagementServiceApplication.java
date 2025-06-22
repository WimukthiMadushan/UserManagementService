package com.usermanagement.usermanagmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class, args);
        System.out.println("Spring Boot SanChaara backend User Authentication started!");
        System.out.println("Access API at http://localhost:8083/api/auth/...");
    }
}
