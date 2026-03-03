package com.wipro.amazecare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.amazecare.dto.LoginRequestDto;
import com.wipro.amazecare.dto.LoginResponseDto;
import com.wipro.amazecare.dto.RegisterRequestDto;
import com.wipro.amazecare.service.AuthService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    // Constructor Autowiring
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginDto,
            HttpSession session) {

        LoginResponseDto response = authService.login(loginDto);

        session.setAttribute("USER", response.getEmail());

        return ResponseEntity.ok(response);
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequestDto registerDto) {

        return ResponseEntity.ok(authService.register(registerDto));
    }
}