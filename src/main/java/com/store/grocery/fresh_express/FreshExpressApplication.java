package com.store.grocery.fresh_express;

import com.store.grocery.fresh_express.service.impl.RoleInitializationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class FreshExpressApplication implements CommandLineRunner {

	private final RoleInitializationService roleInitializationService;

	public FreshExpressApplication(RoleInitializationService roleInitializationService) {
		this.roleInitializationService = roleInitializationService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FreshExpressApplication.class, args);
	}

	@Override
	public void run(String... args) {
		roleInitializationService.initializeRoles();
	}
}
