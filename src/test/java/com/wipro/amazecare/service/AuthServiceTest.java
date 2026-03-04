package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.amazecare.dto.LoginRequestDto;
import com.wipro.amazecare.dto.LoginResponseDto;
import com.wipro.amazecare.dto.RegisterRequestDto;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceTest {

    @Autowired
    private AuthService authService;
    

    @Test
    void testRegisterUser() {

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setEmail("testpatient@gmail.com");
        dto.setPassword("123456");
        dto.setRole("ROLE_PATIENT");

        String response = authService.register(dto);

        assertEquals("User Registered Successfully", response);
    }

    @Test
    void testLoginUser() {

        LoginRequestDto login = new LoginRequestDto();
        login.setEmail("admin@amazecare.com");
        login.setPassword("admin123");

        LoginResponseDto response = authService.login(login);

        assertEquals("Login Successful", response.getMessage());
        assertEquals("ROLE_ADMIN", response.getRole());
    }
}