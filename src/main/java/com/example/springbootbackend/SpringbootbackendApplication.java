package com.example.springbootbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RestController
public class SpringbootbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootbackendApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "Spring boot is working!";
	}

	@GetMapping("/testingCustom")
	public int testNumber(){
		return 23;
	}

	@Configuration
	public class AppConfig {
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	}
}
