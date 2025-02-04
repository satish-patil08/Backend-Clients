package com.microservices.backend_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.microservices"})
@EnableFeignClients
@EnableDiscoveryClient
@EnableMongoRepositories
public class BackendClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendClientApplication.class, args);
    }

}
