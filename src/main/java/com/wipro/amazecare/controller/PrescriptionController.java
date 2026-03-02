package com.wipro.amazecare.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.PrescriptionDto;
import com.wipro.amazecare.service.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<PrescriptionDto> addPrescription(
            @Valid @RequestBody PrescriptionDto dto) {
        return ResponseEntity.ok(prescriptionService.addPrescription(dto));
    }

    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT')")
    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<List<PrescriptionDto>> getByConsultation(
            @PathVariable Long consultationId) {
        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByConsultation(consultationId));
    }
    @PreAuthorize("hasRole('DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok("Prescription deleted successfully");
    }
}