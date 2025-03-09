package com.petcare.backend.proyectoIntegrador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
// @EnableJpaRepositories(basePackages =
// "com.petcare.backend.proyectoIntegrador.repository")
@EnableTransactionManagement
public class ProyectoIntegradorApplication {

	public static void main(String[] args) {		
		SpringApplication.run(ProyectoIntegradorApplication.class, args);
	}

}
