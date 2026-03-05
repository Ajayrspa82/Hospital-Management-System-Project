package com.wipro.amazecare.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.MedicalTestDto;
import com.wipro.amazecare.service.MedicalTestService;

@RestController
@RequestMapping("/api/medical-tests")
public class MedicalTestController {

    private final MedicalTestService medicalTestService;

    public MedicalTestController(MedicalTestService medicalTestService) {
        this.medicalTestService = medicalTestService;
    }

    // ADMIN and DOCTOR can add medical test
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PostMapping
    public ResponseEntity<MedicalTestDto> addMedicalTest(
            @Valid @RequestBody MedicalTestDto dto) {

        return ResponseEntity.ok(medicalTestService.createMedicalTest(dto));
    }

    // ADMIN, DOCTOR and PATIENT can view medical tests
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<List<MedicalTestDto>> getByConsultation(
            @PathVariable Long consultationId) {

        return ResponseEntity.ok(
                medicalTestService.getByConsultation(consultationId));
    }

    // ADMIN and DOCTOR can update
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MedicalTestDto> updateMedicalTest(
            @PathVariable Long id,
            @Valid @RequestBody MedicalTestDto dto) {

        return ResponseEntity.ok(
                medicalTestService.updateMedicalTest(id, dto));
    }


    // ADMIN and DOCTOR can delete

    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicalTest(@PathVariable Long id) {

        medicalTestService.deleteMedicalTest(id);
        return ResponseEntity.ok("Medical test deleted successfully");
    }
}