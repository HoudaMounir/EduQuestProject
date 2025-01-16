package com.example.EduQuest.security;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080") // Keycloak server URL
                .realm("master") // Use the master realm for admin access
                .clientId("admin-cli") // Built-in client for admin operations
                .username("admin") // Keycloak admin username
                .password("admin") // Keycloak admin password
                .build();
    }
}