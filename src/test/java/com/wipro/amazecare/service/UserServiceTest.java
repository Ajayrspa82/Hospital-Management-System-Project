package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.amazecare.dto.UserDto;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testUpdateUser() {

        UserDto dto = new UserDto();
        dto.setEmail("updatedadmin@amazecare.com");

        UserDto updated = userService.updateUser(1L, dto);

        assertEquals("updatedadmin@amazecare.com", updated.getEmail());
    }
}