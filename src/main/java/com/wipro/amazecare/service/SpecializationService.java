package com.wipro.amazecare.service;

import java.util.List;

import com.wipro.amazecare.dto.SpecializationDto;

public interface SpecializationService {

    SpecializationDto createSpecialization(SpecializationDto dto);
    List<SpecializationDto> getAllSpecializations();
}