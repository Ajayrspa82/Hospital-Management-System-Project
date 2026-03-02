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

import com.wipro.amazecare.dto.DoctorDto;
import com.wipro.amazecare.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Create Doctor
    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(
            @Valid @RequestBody DoctorDto doctorDto) {

        DoctorDto savedDoctor = doctorService.createDoctor(doctorDto);
        return ResponseEntity.ok(savedDoctor);
    }

    // Get Doctor By ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {

        DoctorDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    // Get All Doctors
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {

        List<DoctorDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    // Update Doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorDto doctorDto) {

        DoctorDto updatedDoctor = doctorService.updateDoctor(id, doctorDto);
        return ResponseEntity.ok(updatedDoctor);
    }

    // Delete Doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {

        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }
}