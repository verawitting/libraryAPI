package com.example.libraryapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;

@Service
public class SecretService {

    private final VaultTemplate vaultTemplate;

    public SecretService(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }
    
    public String getDbPassword() {

        Map<String, Object> response = vaultTemplate
                .read("secret/data/library")
                .getData();

        @SuppressWarnings("unchecked")
        Map<String, Object> inner = (Map<String, Object>) response.get("data");

        return (String) inner.get("password");
    }
}
