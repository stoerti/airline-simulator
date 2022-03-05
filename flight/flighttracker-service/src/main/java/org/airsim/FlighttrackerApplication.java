package org.airsim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlighttrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlighttrackerApplication.class, args);
	}

}
