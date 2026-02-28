package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.SpecializationDto;
import com.wipro.amazecare.entity.Specialization;
import com.wipro.amazecare.repository.SpecializationRepository;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository repository;

    public SpecializationServiceImpl(SpecializationRepository repository) {
        this.repository = repository;
    }

    @Override
    public SpecializationDto createSpecialization(SpecializationDto dto) {
        Specialization specialization = new Specialization();
        specialization.setSpecializationName(dto.getSpecializationName());
        repository.save(specialization);
        dto.setSpecializationId(specialization.getSpecializationId());
        return dto;
    }

    @Override
    public List<SpecializationDto> getAllSpecializations() {
        return repository.findAll().stream().map(s -> {
            SpecializationDto dto = new SpecializationDto();
            dto.setSpecializationId(s.getSpecializationId());
            dto.setSpecializationName(s.getSpecializationName());
            return dto;
        }).collect(Collectors.toList());
    }
}