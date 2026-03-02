package com.wipro.amazecare.service;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.UserDto;
import com.wipro.amazecare.entity.User;
import com.wipro.amazecare.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<UserDto> list = new ArrayList<>();

        for (User user : userRepository.findAll()) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole().getName());
            list.add(dto);
        }

        return list;
    }

    @Override
    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"+id));

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().getName());

        return dto;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

       
        user.setEmail(userDto.getEmail());

        userRepository.save(user);

        UserDto updatedDto = new UserDto();
        updatedDto.setId(user.getId());
        updatedDto.setEmail(user.getEmail());
        updatedDto.setRole(user.getRole().getName());

        return updatedDto;
    }
}