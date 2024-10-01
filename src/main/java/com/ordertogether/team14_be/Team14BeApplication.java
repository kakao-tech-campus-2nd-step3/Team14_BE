package com.ordertogether.team14_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan
@SpringBootApplication
public class Team14BeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team14BeApplication.class, args);
	}
}
