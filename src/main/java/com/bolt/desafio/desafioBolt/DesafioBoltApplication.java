package com.bolt.desafio.desafioBolt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DesafioBoltApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBoltApplication.class, args);
	}

}
