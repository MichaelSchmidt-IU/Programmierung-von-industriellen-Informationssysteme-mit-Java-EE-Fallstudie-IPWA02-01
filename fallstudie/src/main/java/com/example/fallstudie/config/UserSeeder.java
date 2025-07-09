package com.example.fallstudie.config;

import com.example.fallstudie.model.User;
import com.example.fallstudie.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
public class UserSeeder {

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepo) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User(
                    "admin",
                    encoder.encode("admin123"),
                    List.of("ADMIN"),
                    "Germany"
                );
                userRepo.save(admin);
                System.out.println(" Admin-Nutzer 'admin' wurde erstellt.");
            }

            if (userRepo.findByUsername("scientist").isEmpty()) {
                User scientist = new User(
                    "scientist",
                    encoder.encode("test123"),
                    List.of("SCIENTIST"),
                    "Germany"
                );
                userRepo.save(scientist);
                System.out.println(" Scientist-Nutzer 'scientist' wurde erstellt.");
            }

            if (userRepo.findByUsername("user").isEmpty()) {
                User normalUser = new User(
                    "user",
                    encoder.encode("user123"),
                    List.of("USER"),
                    "Germany"
                );
                userRepo.save(normalUser);
                System.out.println(" Standard-Nutzer 'user' wurde erstellt.");
            }
        };
    }
}
