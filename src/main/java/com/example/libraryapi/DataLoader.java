package com.example.libraryapi;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@Component
public class DataLoader implements CommandLineRunner{
    
    @Autowired
    private VaultTemplate vaultTemplate;

    @Override
    public void run(String... args) throws Exception {
        seedUserToVault();
    }

    private void seedUserToVault() {
        VaultKeyValueOperations keyValueOperations = vaultTemplate.opsForKeyValue("secret",
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);

        System.out.println();
        System.out.println("Post secret" + Collections.singletonMap("user",
                "pastaword").toString() + " to vault");
        System.out.println();

        keyValueOperations.put("secret", Collections.singletonMap("user",
                "pastaword"));

        VaultResponse read = keyValueOperations.get("secret");
        System.out.println("Value of user password from vault [" +
                read.getRequiredData().get("user") + "]");
    }
}
