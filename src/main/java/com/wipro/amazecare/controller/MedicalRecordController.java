package com.wipro.amazecare.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.MedicalRecordDto;
import com.wipro.amazecare.service.MedicalRecordService;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    // ADMIN, DOCTOR and PATIENT can view a patient's record
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<MedicalRecordDto> getMedicalRecordByPatient(
            @PathVariable Long patientId) {

        MedicalRecordDto record =
                medicalRecordService.getMedicalRecordByPatient(patientId);

        return ResponseEntity.ok(record);
    }

    // ADMIN, DOCTOR and PATIENT can view all records
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping("/patient/{patientId}/all")
    public ResponseEntity<List<MedicalRecordDto>> getAllRecordsByPatient(
            @PathVariable Long patientId) {

        List<MedicalRecordDto> records =
                medicalRecordService.getRecordsByPatient(patientId);

        return ResponseEntity.ok(records);
    }

    // ADMIN and DOCTOR can create medical records
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PostMapping
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(
            @Valid @RequestBody MedicalRecordDto dto) {

        MedicalRecordDto created =
                medicalRecordService.createRecord(dto);

        return ResponseEntity.ok(created);
    }

    // ADMIN and DOCTOR can update
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PutMapping("/{recordId}")
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(
            @PathVariable Long recordId,
            @Valid @RequestBody MedicalRecordDto dto) {

        MedicalRecordDto updated =
                medicalRecordService.updateRecord(recordId, dto);

        return ResponseEntity.ok(updated);
    }

    // Only ADMIN can delete records
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{recordId}")
    public ResponseEntity<String> deleteMedicalRecord(
            @PathVariable Long recordId) {

        medicalRecordService.deleteRecord(recordId);

        return ResponseEntity.ok("Medical record deleted successfully");
    }
}