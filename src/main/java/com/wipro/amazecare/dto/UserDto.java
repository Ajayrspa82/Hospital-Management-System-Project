package com.wipro.amazecare.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDto {

    private Long id;
    private String email;
    private String role;

    public UserDto() {
    }
   

    public Long getId() {
        return id;
    }
    @NotBlank

    public String getEmail() {
        return email;
    }
    @NotBlank

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}