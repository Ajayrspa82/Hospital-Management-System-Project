package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.amazecare.dto.PatientDto;

@SpringBootTest
@ActiveProfiles("test")
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Test
    void testCreatePatient() {

        PatientDto patient = new PatientDto();
        patient.setFullName("Ajay");
        patient.setDateOfBirth(LocalDate.of(2000, 1, 1));
        patient.setGender("Male");
        patient.setMobileNumber("9876543210");
        patient.setEmail("ajay@test.com");
        patient.setMedicalHistory("None");

        PatientDto saved = patientService.createPatient(patient);

        assertNotNull(saved.getPatientId());
        assertEquals("Ajay", saved.getFullName());
    }

    @Test
    void testGetAllPatients() {

        List<PatientDto> patients = patientService.getAllPatients();
        assertNotNull(patients);
    }
}