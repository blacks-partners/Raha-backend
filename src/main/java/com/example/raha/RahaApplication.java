package com.example.raha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class RahaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RahaApplication.class, args);
	}

}
