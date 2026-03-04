package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.amazecare.dto.SpecializationDto;

@SpringBootTest
@ActiveProfiles("test")
class SpecializationServiceTest {

    @Autowired
    private SpecializationService specializationService;

    @Test
    void testCreateSpecialization() {

        SpecializationDto dto = new SpecializationDto();
        dto.setSpecializationName("Neurology");

        SpecializationDto saved = specializationService.createSpecialization(dto);

        assertNotNull(saved.getSpecializationId());
        assertEquals("Neurology", saved.getSpecializationName());
    }

    @Test
    void testGetAllSpecializations() {

        List<SpecializationDto> list = specializationService.getAllSpecializations();
        assertNotNull(list);
    }
}