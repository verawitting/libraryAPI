package com.example.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class VaultConfig {

    @Bean
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication("my-dev-root-token");
    }

    @Bean
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint endpoint = new VaultEndpoint();
        endpoint.setHost("localhost");
        endpoint.setPort(8200);
        endpoint.setScheme("http");
        return endpoint;
    }

    @Bean
    public VaultTemplate vaultTemplate() {
        return new VaultTemplate(
                vaultEndpoint(),
                new TokenAuthentication("my-dev-root-token"));
    }
}
