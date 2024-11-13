package com.pinapp.ms_customers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("API REST Customers")
                                .version("1.0.0")
                                .description("Technical Challenge Backend Developer with Java Knowledge"))
                        .addServersItem(new Server().url("https://pinapp-ms.ue.r.appspot.com"));
        }
}