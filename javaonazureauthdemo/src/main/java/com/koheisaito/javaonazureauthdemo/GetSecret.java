package com.koheisaito.javaonazureauthdemo;

import java.util.logging.Logger;

import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetSecret {
    private static final String SECRET_NAME = "storageaccountconnectionstring";
    private static final String keyVaultName = "key-b1f4eaa7158ef33d";

    Logger logger = Logger.getLogger(GetSecret.class.getName());

    private String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";

    @GetMapping("/secrets")
    public void getSecret() {
        DefaultAzureCredential defaultAzureCredential = buildCredential();
        SecretClient secretClient = buildSecretClient(keyVaultUri, defaultAzureCredential);
        KeyVaultSecret keyVaultSecret = secretClient.getSecret(SECRET_NAME);
        String secret = keyVaultSecret.getValue();
        logger.info("Secret: " + secret);
    }

    public DefaultAzureCredential buildCredential() {
        DefaultAzureCredential defaultAzureCredential = new DefaultAzureCredentialBuilder()
            .build();
        return defaultAzureCredential;
    }

    public SecretClient buildSecretClient(String vaultUrl, DefaultAzureCredential credential) {
        SecretClient client = new SecretClientBuilder()
            .vaultUrl(vaultUrl)
            .credential(credential)
            .buildClient();
        return client;
    }
}
