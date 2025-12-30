package com.tc.demo.resources;

import com.azure.core.http.rest.PagedIterable;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.SecretProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class KVClient {

    SecretClient keyVaultClient;

    HashMap<String, List<String>> versions = new HashMap();

    private void prepClient(String vaultName, String tenantId, String clientId, String clientSecret) {
        String vaultUri = String.format("https://%s.vault.azure.net/", vaultName);
        this.keyVaultClient = (new SecretClientBuilder()).vaultUrl(vaultUri).credential(((ClientSecretCredentialBuilder)((ClientSecretCredentialBuilder)(new ClientSecretCredentialBuilder()).tenantId(tenantId)).clientId(clientId)).clientSecret(clientSecret).build()).buildClient();
    }

    private void prepClient(String vaultName) {
        String vaultUri = String.format("https://%s.vault.azure.net/", vaultName);
        this.keyVaultClient = (new SecretClientBuilder()).vaultUrl(vaultUri).credential((new DefaultAzureCredentialBuilder()).build()).buildClient();
    }

    public KVClient(String vaultName, String tenantId, String clientId, String clientSecret) {
        this.prepClient(vaultName, tenantId, clientId, clientSecret);
    }

    public KVClient(String vaultName) {
        this.prepClient(vaultName);
    }

    void refreshVersionList(String keyName) {
        List<SecretProperties> newVersionList = new ArrayList<>();
        PagedIterable<SecretProperties> var10000 = this.keyVaultClient.listPropertiesOfSecretVersions(keyName);
        Objects.requireNonNull(newVersionList);
        var10000.forEach(newVersionList::add);
        this.versions.put(
                keyName,
                newVersionList.stream()
                        .sorted((sp1, sp2) -> sp2.getCreatedOn()
                                .compareTo(sp1.getCreatedOn())).map(SecretProperties::getVersion).toList());
    }
}
