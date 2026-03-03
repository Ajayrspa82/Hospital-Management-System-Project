package com.wipro.amazecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.LoginRequestDto;
import com.wipro.amazecare.dto.LoginResponseDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginDto, HttpSession session) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

            authenticationManager.authenticate(authToken);

            session.setAttribute("USER", loginDto.getEmail());

            LoginResponseDto response = new LoginResponseDto();
            response.setEmail(loginDto.getEmail());
            response.setMessage("Login successful");

            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            LoginResponseDto response = new LoginResponseDto();
            response.setMessage("Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
    }
}