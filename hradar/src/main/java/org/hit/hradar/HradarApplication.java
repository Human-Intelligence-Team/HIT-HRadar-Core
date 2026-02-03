package org.hit.hradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HradarApplication {

	public static void main(String[] args) {
		SpringApplication.run(HradarApplication.class, args);
	}

}
