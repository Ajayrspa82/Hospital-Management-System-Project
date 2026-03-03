package com.wipro.amazecare.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wipro.amazecare.entity.Role;
import com.wipro.amazecare.entity.User;
import com.wipro.amazecare.entity.Admin;
import com.wipro.amazecare.repository.RoleRepository;
import com.wipro.amazecare.repository.UserRepository;
import com.wipro.amazecare.repository.AdminRepository;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository,
                      UserRepository userRepository,
                      AdminRepository adminRepository,
                      PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_DOCTOR");
        createRoleIfNotFound("ROLE_PATIENT");

        if (userRepository.findByEmail("admin@amazecare.com").isEmpty()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
            User user = new User();
            user.setEmail("admin@amazecare.com");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRole(adminRole);

            User savedUser = userRepository.save(user);

            if (adminRepository.count() == 0) {
                Admin admin = new Admin();
                admin.setUser(savedUser);
                adminRepository.save(admin);
            }
            System.out.println("Default Admin Created!");
        }
    }

    private void createRoleIfNotFound(String roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            roleRepository.save(new Role(roleName));
            System.out.println(roleName + " Created!");
        }
    }
}