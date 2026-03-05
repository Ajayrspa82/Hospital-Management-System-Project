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

    // ADMIN and DOCTOR can add prescription
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PostMapping
    public ResponseEntity<PrescriptionDto> addPrescription(
            @Valid @RequestBody PrescriptionDto dto) {
        return ResponseEntity.ok(prescriptionService.addPrescription(dto));
    }

    // ADMIN, DOCTOR and PATIENT can view prescriptions
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<List<PrescriptionDto>> getByConsultation(
            @PathVariable Long consultationId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByConsultation(consultationId));
    }

    // ADMIN and DOCTOR can delete prescription
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(@PathVariable Long id) {

        prescriptionService.deletePrescription(id);
        return ResponseEntity.ok("Prescription deleted successfully");
    }
}