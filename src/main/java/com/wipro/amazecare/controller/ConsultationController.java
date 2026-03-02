package com.wipro.amazecare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.ConsultationDto;
import com.wipro.amazecare.service.ConsultationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<ConsultationDto> createConsultation(
            @Valid @RequestBody ConsultationDto dto) {
        return ResponseEntity.ok(consultationService.createConsultation(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDto> getConsultation(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.getConsultationById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ConsultationDto>> getByPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(consultationService.getByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ConsultationDto>> getByDoctor(
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(consultationService.getByDoctor(doctorId));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ConsultationDto> updateConsultation(
            @PathVariable Long id,
            @Valid @RequestBody ConsultationDto dto) {
        return ResponseEntity.ok(consultationService.updateConsultation(id, dto));
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.ok("Consultation deleted successfully");
    }
}