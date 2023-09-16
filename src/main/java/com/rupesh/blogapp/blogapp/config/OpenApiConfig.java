package com.rupesh.blogapp.blogapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	String schemeName = "bearerScheme";
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement()
						.addList(schemeName))
				.components(new Components()
						.addSecuritySchemes(schemeName, new SecurityScheme()
								.name(schemeName)
								.type(SecurityScheme.Type.HTTP)
								.bearerFormat("JWT")
								.scheme("bearer")))
		.info(new Info()
				.description("BlogApp - Java Spring Boot Backend")
				.title("BlogApp Apis")
				.version("Spring Boot Version 3.1.3")
				.termsOfService("Terms of Service")
				.contact(new Contact().name("Rupesh").email("rupesh.baranwal24@gmail.com"))
				)//.servers(new Server().description("Local Environment").url("http//localhost:8080"))
		;
						
						
	}
}
