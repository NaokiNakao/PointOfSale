package com.nakao.pointofsale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PointOfSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointOfSaleApplication.class, args);
	}

}
