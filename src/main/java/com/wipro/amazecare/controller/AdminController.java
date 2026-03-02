package com.wipro.amazecare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.DoctorDto;
import com.wipro.amazecare.dto.PatientDto;
import com.wipro.amazecare.dto.AppointmentResponseDto;
import com.wipro.amazecare.service.DoctorService;
import com.wipro.amazecare.service.PatientService;
import com.wipro.amazecare.service.AppointmentService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")

public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    // ================== DOCTOR MANAGEMENT ==================

    @PostMapping("/doctors")
    public ResponseEntity<Object> addDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.addDoctor(doctorDto));
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDto doctorDto) {

        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDto));
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully");
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    // ================== PATIENT MANAGEMENT ==================

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully");
    }

    // ================== APPOINTMENT MANAGEMENT ==================

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
}