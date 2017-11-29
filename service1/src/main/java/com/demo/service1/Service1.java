package com.demo.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class Service1 {

    @GetMapping("service1")
    public String service1() {
        return "service1";
    }

    public static void main(String[] args) {
        SpringApplication.run(Service1.class, args);
    }
}
