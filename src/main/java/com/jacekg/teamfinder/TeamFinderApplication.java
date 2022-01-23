package com.jacekg.teamfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:application.properties", "classpath:secret.properties"})
public class TeamFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamFinderApplication.class, args);
	}

}
