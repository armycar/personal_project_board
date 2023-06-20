package com.example.mini_project_cm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiniProjectCmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniProjectCmApplication.class, args);
	}

}
