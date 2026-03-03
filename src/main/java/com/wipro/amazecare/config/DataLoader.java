package com.wipro.amazecare.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wipro.amazecare.entity.Role;
import com.wipro.amazecare.entity.User;
import com.wipro.amazecare.repository.RoleRepository;
import com.wipro.amazecare.repository.UserRepository;

@Component
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
    public void run(String... args) throws Exception {

        // ✅ Create Roles if not exist
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_DOCTOR");
        createRoleIfNotExists("ROLE_PATIENT");

        // ✅ Create Default Admin User
        if (userRepository.findByEmail("admin@amazecare.com").isEmpty()) {

            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

            User adminUser = new User();
            adminUser.setEmail("admin@amazecare.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(adminRole);

            userRepository.save(adminUser);

            System.out.println("✅ Default Admin Created!");
            System.out.println("Email: admin@amazecare.com");
            System.out.println("Password: admin123");
        } else {
            System.out.println("ℹ️ Admin already exists.");
        }
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("✅ Role created: " + roleName);
        }
    }
}