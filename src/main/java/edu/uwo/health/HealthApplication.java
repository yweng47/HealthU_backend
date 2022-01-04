package edu.uwo.health;

import edu.uwo.health.utils.RSAUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableOpenApi
public class HealthApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		RSAUtils.genKeyPair();
		SpringApplication.run(HealthApplication.class, args);
	}

}
