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

@Component
public class DataLoader implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

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
    public void run(String... args) throws Exception {

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        if (userRepository. findByEmail("admin").isEmpty()) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();

            User user = new User();
            user.setEmail("admin");
            user.setPassword(passwordEncoder.encode("admin123")); // 🔐 ENCRYPTED
            user.setRole(adminRole);

            User savedUser = userRepository.save(user);

            Admin admin = new Admin();
            admin.setUser(savedUser);

            adminRepository.save(admin);

            System.out.println("Default Admin Created!");
        }
    }
}