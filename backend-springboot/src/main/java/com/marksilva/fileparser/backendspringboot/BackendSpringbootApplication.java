package com.marksilva.fileparser.backendspringboot;

import com.marksilva.fileparser.backendspringboot.models.Role;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.RoleRepository;
import com.marksilva.fileparser.backendspringboot.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BackendSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSpringbootApplication.class, args);
    }

    /**
     * Create all the roles needed for the application and store it into the database
     *
     * @param roleRepository
     * @param userRepository
     * @param passwordEncoder
     * @return
     */
    @Bean
    public CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Checks if roles have already been created, so it does not create it twice
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(new Role("USER"));

            //TODO: Find a way to setup without storing information in main file
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setAuthorities(roles);

            userRepository.save(admin);

        };
    }
}
