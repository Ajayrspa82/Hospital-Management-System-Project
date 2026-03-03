package com.wipro.amazecare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.amazecare.dto.PatientDto;
import com.wipro.amazecare.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Create Patient
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(
            @Valid @RequestBody PatientDto patientDto) {

        PatientDto savedPatient = patientService.createPatient(patientDto);
        return ResponseEntity.ok(savedPatient);
    }

    // Get Patient By ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long id) {

        PatientDto patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    // Get All Patients
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {

        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    // Update Patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientDto patientDto) {

        PatientDto updatedPatient = patientService.updatePatient(id, patientDto);
        return ResponseEntity.ok(updatedPatient);
    }

    // Delete Patient
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }
}