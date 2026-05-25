package com.example.libraryapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@Service
public class SecretService {

    private final VaultTemplate vaultTemplate;

    public SecretService(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    public String getDbPassword() {

        VaultResponse response = vaultTemplate.read("secret/data/library");

        if (response == null || response.getData() == null) {
            throw new IllegalStateException("No data found in Vault at secret/data/library");
        }

        Map<String, Object> outer = response.getData();

        @SuppressWarnings("unchecked")
        Map<String, Object> inner =
                (Map<String, Object>) outer.get("data");

        return (String) inner.get("password");
    }
}
