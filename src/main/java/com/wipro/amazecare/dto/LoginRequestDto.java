package com.wipro.amazecare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {

    private String email;
    private String password;

    public LoginRequestDto() {
    }
    @Email
    @NotBlank

    public String getEmail() {
        return email;
    }
    @NotBlank
	public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}