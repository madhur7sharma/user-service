package com.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(UserServiceApplication.class, args);
		} catch (Exception e) {
			System.out.println();
		}
	}

}
