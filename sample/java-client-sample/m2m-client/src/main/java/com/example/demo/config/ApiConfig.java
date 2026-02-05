package com.example.demo.config;

import hu.gov.nav.TokenServiceApi;
import hu.gov.nav.UserregistrationServiceApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public TokenServiceApi tokenServiceApi() {
        return new TokenServiceApi();
    }

    @Bean
    public UserregistrationServiceApi userregistrationServiceApi() {
        return new UserregistrationServiceApi();
    }

}
