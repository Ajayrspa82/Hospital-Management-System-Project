package com.wipro.amazecare.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wipro.amazecare.entity.*;
import com.wipro.amazecare.repository.*;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

        roleRepository.findByName("ROLE_DOCTOR")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_DOCTOR");
                    return roleRepository.save(role);
                });

        roleRepository.findByName("ROLE_PATIENT")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_PATIENT");
                    return roleRepository.save(role);
                });

        userRepository.findByEmail("admin@amazecare.com")
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail("admin@amazecare.com");
                    user.setPassword(passwordEncoder.encode("admin123"));
                    user.setRole(adminRole);
                    return userRepository.save(user);
                });
    }
}