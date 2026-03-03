package com.wipro.amazecare.controller;

import javax.naming.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.amazecare.dto.LoginRequestDto;
import com.wipro.amazecare.dto.LoginResponseDto;
import com.wipro.amazecare.entity.User;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginDto,
            HttpSession session) throws AuthenticationException {

        org.springframework.security.core.Authentication authentication =
		        authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                        loginDto.getEmail(),
		                        loginDto.getPassword()
		                ));

		User user = (User) authentication.getPrincipal();

		session.setAttribute("USER", user.getEmail());

		LoginResponseDto response = new LoginResponseDto();
		response.setEmail(user.getEmail());
		response.setRole(user.getRole().getName());
		response.setMessage("Login successful");

		return ResponseEntity.ok(response);
    }
}