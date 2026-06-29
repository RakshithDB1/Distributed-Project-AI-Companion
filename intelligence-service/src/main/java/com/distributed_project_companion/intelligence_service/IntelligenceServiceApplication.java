package com.distributed_project_companion.intelligence_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.distributed_project_companion"})
@EnableFeignClients
public class IntelligenceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligenceServiceApplication.class, args);
	}

}
