# akv-Spring-boot-4-issue

## Overview

This project was created to highlight an issue initially observed in a larger project, though in the course of recreating the issue,
the cause of the issue was identified: a dependency `io.netty:netty-codec-http:4.2.8.Final`

This dependency causes the following exception:

```
java.lang.IllegalArgumentException: The URI contain illegal characters: /secrets/[secret-name]/versions?api-version=7.6&$skiptoken=[token]
```

to be thrown when an Azure `SecretClient` attempts to call the `listPropertiesOfSecretVersions(String)` method.

## Prerequisites

* Java 21 (Java 17 might work)
* Gradle 8.14 or above

## Environment Variables

The property `kv.jwt.key-storage.strategy` determines which environment variables are needed and it can be of two values:
* AKV
* AKV-pwl

Regardless, the following environment variables need to be set
* AKV_VAULT_NAME - the name of the Key Vault
* AKV_SECRET_NAME - the name of the Secret being stored

If `kv.jwt.key-storage.strategy` is set to `AKV-pwl`, you should be good to go as long as you have an app authentication mechanism set up (and you may want to remove the other properties)
If `kv.jwt.key-storage.strategy` is set to `AKV`, then passwordless is **NOT** being used and you'll need to set these variables as well:

* AKV_TENANT_ID - the tenant of your credential
* AKV_CLIENT_ID - the client is
* AKV_CLIENT_SECRET - the client secret
