package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.amazecare.dto.ConsultationDto;
import com.wipro.amazecare.entity.Doctor;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.entity.Specialization;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.repository.SpecializationRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ConsultationServiceTest {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    private Patient patient;
    private Doctor doctor;

    @BeforeEach
    void setUp() {

        Specialization specialization = new Specialization();
        specialization.setSpecializationName("Cardiology");
        specialization = specializationRepository.save(specialization);

        doctor = new Doctor();
        doctor.setName("Dr Test");
        doctor.setSpecialization(specialization);
        doctor = doctorRepository.save(doctor);

        patient = new Patient();
        patient.setFullName("Test Patient");
        patient = patientRepository.save(patient);
    }

    @Test
    void testCreateConsultation() {

        ConsultationDto dto = new ConsultationDto();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setPatientId(patient.getPatientId());
        dto.setSymptoms("Fever");
        dto.setConsultationDate(LocalDateTime.now());

        ConsultationDto result =
                consultationService.createConsultation(dto);

        assertNotNull(result);
        assertNotNull(result.getConsultationId());
        assertEquals("Fever", result.getSymptoms());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(doctor.getDoctorId(), result.getDoctorId());
    }

   }