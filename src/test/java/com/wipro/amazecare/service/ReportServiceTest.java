package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.amazecare.dto.ReportDto;
import com.wipro.amazecare.entity.*;
import com.wipro.amazecare.repository.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReportServiceSingleTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @BeforeEach
    void setUp() {

        Specialization specialization = new Specialization();
        specialization.setSpecializationName("General");
        specialization = specializationRepository.save(specialization);

        Doctor doctor = new Doctor();
        doctor.setName("Dr Test");
        doctor.setSpecialization(specialization);
        doctor = doctorRepository.save(doctor);

        Patient patient = new Patient();
        patient.setFullName("Test Patient");
        patient = patientRepository.save(patient);

        Consultation consultation = new Consultation();
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setSymptoms("Fever");
        consultationRepository.save(consultation);
    }

    @Test
    void testGenerateSystemReport() {
        ReportDto report = reportService.generateSystemReport();

        assertNotNull(report);
        assertTrue(report.getTotalConsultations() >= 1);
    }
}