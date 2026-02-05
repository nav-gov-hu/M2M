package com.example.demo;

import com.example.demo.service.M2mApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        System.setProperty("javax.net.ssl.trustStore","nav-truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword","changeit");
        M2mApiService service = applicationContext.getBean(M2mApiService.class);
        service.start();
	}

}
