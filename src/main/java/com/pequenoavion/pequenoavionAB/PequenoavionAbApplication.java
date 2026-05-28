package com.pequenoavion.pequenoavionAB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Activa el reloj automático de Spring Boot
public class PequenoavionAbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PequenoavionAbApplication.class, args);
	}

}
