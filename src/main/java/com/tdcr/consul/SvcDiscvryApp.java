package com.tdcr.consul;

import com.tdcr.consul.config.AppPropsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(value = AppPropsConfig.class)
public class SvcDiscvryApp {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(SvcDiscvryApp.class, args);
	}

}
