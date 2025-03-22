package com.fever.event.test.fever_demo_miguelcabezas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.fever.event.test.fever_demo_miguelcabezas")
public class FeverDemoMiguelcabezasApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeverDemoMiguelcabezasApplication.class, args);
	}

}
