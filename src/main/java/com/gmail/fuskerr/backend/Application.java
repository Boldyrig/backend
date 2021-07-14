package com.gmail.fuskerr.backend;

import com.gmail.fuskerr.backend.domain.User;
import com.gmail.fuskerr.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return (args) -> {
            userRepository.save(new User("name2", "token2"));
        };
    }
}
