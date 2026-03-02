package com.wipro.amazecare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.amazecare.dto.SpecializationDto;
import com.wipro.amazecare.service.SpecializationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    // Create Specialization
    @PostMapping
    public ResponseEntity<SpecializationDto> createSpecialization(
            @Valid @RequestBody SpecializationDto dto) {

        SpecializationDto saved = specializationService.createSpecialization(dto);
        return ResponseEntity.ok(saved);
    }

    // Get All Specializations
    @GetMapping
    public ResponseEntity<List<SpecializationDto>> getAllSpecializations() {

        List<SpecializationDto> list = specializationService.getAllSpecializations();
        return ResponseEntity.ok(list);
    }
}