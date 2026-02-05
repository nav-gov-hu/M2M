package com.example.demo.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptorConfig {
    @Value("${encrypt.secret}")
    private String secret;
    @Value("${encrypt.algorithm}")
    private String algorithm;
    @Value("${encrypt.iterations}")
    private String iterations;
    @Value("${encrypt.pool-size}")
    private String poolSize;
    @Value("${encrypt.provider-name}")
    private String providerName;
    @Value("${encrypt.salt-generator}")
    private String saltGenerator;
    @Value("${encrypt.output-type}")
    private String outputType;

    @Bean
    public PooledPBEStringEncryptor encryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(this.secret);
        config.setAlgorithm(this.algorithm);
        config.setKeyObtentionIterations(this.iterations);
        config.setPoolSize(this.poolSize);
        config.setProviderName(this.providerName);
        config.setSaltGeneratorClassName(this.saltGenerator);
        config.setStringOutputType(this.outputType);
        encryptor.setConfig(config);
        return encryptor;
    }
}

