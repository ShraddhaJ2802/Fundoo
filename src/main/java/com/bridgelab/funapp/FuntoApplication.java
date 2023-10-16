package com.bridgelab.funapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class FuntoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuntoApplication.class, args);
		
		log.info("Welcome funto application");
		log.debug("Debug level");
		log.error("Error levell");
		log.warn("Warning level");
	}

}
