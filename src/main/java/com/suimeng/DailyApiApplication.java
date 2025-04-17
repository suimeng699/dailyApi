package com.suimeng;

import com.suimeng.properties.AIProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AIProperties.class)
public class DailyApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DailyApiApplication.class, args);
	}

}
