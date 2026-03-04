package com.wipro.amazecare.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.UserDto;
@Service
public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

	UserDto updateUser(Long id, UserDto userDto);
}