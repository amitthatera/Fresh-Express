package com.store.grocery.fresh_express;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FreshExpressApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreshExpressApplication.class, args);
	}

}
