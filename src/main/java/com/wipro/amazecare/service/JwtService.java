package com.wipro.amazecare.service;

import com.wipro.amazecare.entity.User;

public interface JwtService {

    String generateToken(User user);

    String extractUsername(String token);

    boolean validateToken(String token, User user);
}