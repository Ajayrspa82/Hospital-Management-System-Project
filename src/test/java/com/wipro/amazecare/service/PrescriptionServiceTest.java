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

import com.wipro.amazecare.dto.PrescriptionDto;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.entity.Doctor;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.entity.Specialization;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.repository.SpecializationRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PrescriptionServiceTest {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    private Consultation consultation;

    @BeforeEach
    void setUp() {

        // Specialization
        Specialization specialization = new Specialization();
        specialization.setSpecializationName("General Medicine");
        specialization = specializationRepository.save(specialization);

        // Doctor
        Doctor doctor = new Doctor();
        doctor.setName("Dr Test");
        doctor.setSpecialization(specialization);
        doctor = doctorRepository.save(doctor);

        // Patient
        Patient patient = new Patient();
        patient.setFullName("Test Patient");
        patient = patientRepository.save(patient);

        // Consultation
        consultation = new Consultation();
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setSymptoms("Fever and Cough");
        consultation.setConsultationDate(LocalDateTime.now());
        consultation = consultationRepository.save(consultation);
    }

    @Test
    void testAddPrescription() {

        PrescriptionDto dto = new PrescriptionDto();
        dto.setConsultationId(consultation.getConsultationId());
        dto.setMedicineName("Paracetamol");
        dto.setDosage("500mg");
        dto.setDuration("5 days");

        PrescriptionDto result = prescriptionService.addPrescription(dto);

        assertNotNull(result);
        assertNotNull(result.getPrescriptionId());
        assertEquals("Paracetamol", result.getMedicineName());
        assertEquals("500mg", result.getDosage());
        assertEquals("5 days", result.getDuration());
        assertEquals(consultation.getConsultationId(), result.getConsultationId());
    }

   
}