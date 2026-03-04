package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wipro.amazecare.dto.DoctorDto;
import com.wipro.amazecare.dto.SpecializationDto;

@SpringBootTest
@ActiveProfiles("test")
class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SpecializationService specializationService;

    @Test
    void testCreateDoctor() {

        // First create specialization
        SpecializationDto spec = new SpecializationDto();
        spec.setSpecializationName("Cardiology");
        SpecializationDto savedSpec = specializationService.createSpecialization(spec);

        // Create doctor
        DoctorDto doctor = new DoctorDto();
        doctor.setName("Dr. John");
        doctor.setQualification("MBBS");
        doctor.setExperience(5);
        doctor.setDesignation("Senior Doctor");
        doctor.setSpecializationId(savedSpec.getSpecializationId());

        DoctorDto savedDoctor = doctorService.createDoctor(doctor);

        assertNotNull(savedDoctor.getDoctorId());
        assertEquals("Dr. John", savedDoctor.getName());
    }

    @Test
    void testGetAllDoctors() {

        List<DoctorDto> doctors = doctorService.getAllDoctors();
        assertNotNull(doctors);
    }
}