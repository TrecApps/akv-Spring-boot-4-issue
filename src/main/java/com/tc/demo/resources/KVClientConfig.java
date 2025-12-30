package com.tc.demo.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KVClientConfig {

    @Bean
    @ConditionalOnProperty(
            prefix = "kv.jwt.key-storage",
            name = {"strategy"},
            havingValue = "AKV"
    )
    KVClient getAKVJwtKeyHolderId(
            @Value("${kv.jwt.vault-name}") String vaultName,
            @Value("${kv.jwt.tenantId}") String tenantId,
            @Value("${kv.jwt.clientId}") String clientId,
            @Value("${kv.jwt.clientSecret}") String clientSecret) {
        KVClient client = new KVClient(vaultName, tenantId, clientId, clientSecret);
        return client;
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "kv.jwt.key-storage",
            name = {"strategy"},
            havingValue = "AKV-pwl"
    )
    KVClient getAKVJwtKeyHolderPwl(
            @Value("${kv.jwt.vault-name}") String vaultName) {
        KVClient client = new KVClient(vaultName);
        return client;
    }
}
