package com.heartsuit.showcase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.heartsuit.showcase.*"})
public class ShowcaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowcaseApplication.class, args);
	}

}
