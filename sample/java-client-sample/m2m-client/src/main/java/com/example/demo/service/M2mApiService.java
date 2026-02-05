package com.example.demo.service;

import hu.gov.nav.*;
import hu.gov.nav.auth.OAuth;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Service
@PropertySource("user.properties")
public class M2mApiService {

    @Autowired
    TokenServiceApi tokenServiceApi;
    @Autowired
    UserregistrationServiceApi userregistrationServiceApi;
    @Autowired
    PooledPBEStringEncryptor encryptor;

    @Value("${user.username}")
    String username;
    @Value("${user.password}")
    String password;
    @Value("${user.clientId}")
    String clientId;
    @Value("${user.clientSecret}")
    String clientSecret;
    @Value("${signature.nonce}")
    String nonce;
    @Value("${signature.first}")
    String signatureFirstPart;

    String token;
    String signatureSecondPart;

    private static final Logger logger = LoggerFactory.getLogger(M2mApiService.class);
    
    public void start() throws Exception {
        login();
        userActivation();
    }

    private void login() {
        CreateTokenRequestBodyType request = new CreateTokenRequestBodyType();
        CreateTokenRequestType requestData = new CreateTokenRequestType();
        requestData.setUsername(username);
        requestData.setPassword(password);
        requestData.setClientId(clientId);
        requestData.setClientSecret(clientSecret);
        request.setRequestData(requestData);

        CreateTokenResponseType result = tokenServiceApi.createToken(request, UUID.randomUUID().toString(), null);
        logger.info("createTokenRequestBodyType:{}, createTokenResponseType:{}", request, result);
        token = result.getAccessToken();
    }

    private void userActivation() {
        CommonApiClient commonApiClient = new CommonApiClient();
        OAuth apiAuth = (OAuth) commonApiClient.getAuthentication("bearerAuth");
        apiAuth.setAccessToken(token);
        userregistrationServiceApi.setApiClient(commonApiClient);

        RedeemNonceRequestBodyType request = new RedeemNonceRequestBodyType();
        RedeemNonceRequestType requestData = new RedeemNonceRequestType();
        requestData.setNonce(nonce);
        request.setRequestData(requestData);

        RedeemNonceResponseType response = userregistrationServiceApi.redeemNonce(request, UUID.randomUUID().toString(), null);
        logger.info("RedeemNonceRequestBodyType:{}, RedeemNonceResponseType:{}", request, response);
        signatureSecondPart = encryptor.encrypt(response.getSignatureKeySecondPart());

        String messageId = UUID.randomUUID().toString();
        ActivateUserRegistrationRequestBodyType requestActivate = new ActivateUserRegistrationRequestBodyType();
        ActivateUserRegistrationRequestType requestDataActivate = new ActivateUserRegistrationRequestType();
        requestDataActivate.setSignature(generateSignature(messageId, ""));
        requestActivate.setRequestData(requestDataActivate);

        ActivateUserRegistrationResponseType responseActivate = userregistrationServiceApi.activateUserRegistration(requestActivate, messageId, null);
        logger.info("ActivateUserRegistrationRequestBodyType:{}, ActivateUserRegistrationResponseType:{}", requestActivate, responseActivate);
    }

    private String generateSignature(String messageId, String baseText) {
        String utcTime = LocalDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String toSign = messageId + utcTime + baseText + signatureFirstPart + encryptor.decrypt(signatureSecondPart);
        logger.info("generateSignature:{}", toSign);

        byte[] hash = DigestUtils.sha256(toSign.getBytes());
        return Base64.getEncoder().encodeToString(hash).toUpperCase();
    }

}
