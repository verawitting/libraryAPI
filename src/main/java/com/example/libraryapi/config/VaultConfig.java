package com.example.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class VaultConfig {

    @Bean
    public VaultTemplate vaultTemplate() {

        String token = System.getenv("VAULT_TOKEN");

        if (token == null || token.isBlank()) {
            throw new IllegalStateException("VAULT_TOKEN environment variable is missing.");
        }

        VaultEndpoint endpoint = new VaultEndpoint();
        endpoint.setHost("127.0.0.1");
        endpoint.setPort(8200);
        endpoint.setScheme("http");

        return new VaultTemplate(
                endpoint,
                new TokenAuthentication(token)
        );
    }
}
