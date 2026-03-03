package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.SpecializationDto;
import com.wipro.amazecare.entity.Specialization;
import com.wipro.amazecare.repository.SpecializationRepository;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public SpecializationDto createSpecialization(SpecializationDto dto) {

        Specialization specialization = new Specialization();
        specialization.setSpecializationName(dto.getSpecializationName());

        Specialization saved = specializationRepository.save(specialization);

        return mapToDto(saved);
    }

    @Override
    public List<SpecializationDto> getAllSpecializations() {

        return specializationRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SpecializationDto mapToDto(Specialization specialization) {

        SpecializationDto dto = new SpecializationDto();
        dto.setSpecializationId(specialization.getSpecializationId());
        dto.setSpecializationName(specialization.getSpecializationName());

        return dto;
    }
}