package com.wipro.amazecare.dto;

public class LoginResponseDto {

    private String email;
    private String role;
    private String message;

    public LoginResponseDto(String email2, String name) {
		// TODO Auto-generated constructor stub
	}
	public LoginResponseDto() {
		// TODO Auto-generated constructor stub
	}
	public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getMessage() { return message; }

    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setMessage(String message) { this.message = message; }
}