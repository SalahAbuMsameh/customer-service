package com.apisoft.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Customer ms application.
 *
 * @author Salah Abu Msameh
 */
@EnableAsync
@SpringBootApplication
@EnableCaching
public class CustomerMsApplication {
	
	/**
	 * app main method.
	 *
	 * @param args app run arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(CustomerMsApplication.class, args);
	}
}
