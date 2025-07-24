package com.java.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.java.bank")
public class OnlineBankingSystem1Application {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBankingSystem1Application.class, args);
	}
	
	static {
		System.out.println("Main Class");
	}

}
