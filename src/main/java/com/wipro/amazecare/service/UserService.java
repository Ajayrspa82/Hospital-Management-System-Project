package com.wipro.amazecare.service;


import java.util.List;

import com.wipro.amazecare.dto.UserDto;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

	UserDto updateUser(Long id, UserDto userDto);
}