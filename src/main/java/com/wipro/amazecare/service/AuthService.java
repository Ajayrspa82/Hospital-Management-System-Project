package com.wipro.amazecare.service;

import com.wipro.amazecare.dto.LoginRequestDto;
import com.wipro.amazecare.dto.LoginResponseDto;
import com.wipro.amazecare.dto.RegisterRequestDto;
import com.wipro.amazecare.dto.UserDto;

public interface AuthService {
    UserDto register(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
}