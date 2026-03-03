package com.wipro.amazecare.dto;

public class LoginResponseDto {

    private String email;
    private String role;

    public LoginResponseDto() {
    	
    }

    public LoginResponseDto(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() { return email; }
    public String getRole() { return role; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }

	public void setMessage(String string) {
		// TODO Auto-generated method stub
		
	}
}