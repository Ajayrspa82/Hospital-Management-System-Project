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

import com.wipro.amazecare.dto.MedicalRecordDto;
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
class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    private Patient patient;
    private Consultation consultation;

    @BeforeEach
    void setUp() {

        Specialization specialization = new Specialization();
        specialization.setSpecializationName("General");
        specialization = specializationRepository.save(specialization);

        Doctor doctor = new Doctor();
        doctor.setName("Dr Test");
        doctor.setSpecialization(specialization);
        doctor = doctorRepository.save(doctor);

        patient = new Patient();
        patient.setFullName("Test Patient");
        patient = patientRepository.save(patient);

        consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setSymptoms("Fever");
        consultation.setConsultationDate(LocalDateTime.now());
        consultation = consultationRepository.save(consultation);
    }

    @Test
    void testCreateRecord() {

        MedicalRecordDto dto = new MedicalRecordDto();
        dto.setPatientId(patient.getPatientId());
        dto.setConsultationId(consultation.getConsultationId());
        dto.setNotes("Patient recovering well");

        MedicalRecordDto result =
                medicalRecordService.createRecord(dto);

        assertNotNull(result);
        assertNotNull(result.getRecordId());
        assertEquals("Patient recovering well", result.getNotes());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(consultation.getConsultationId(), result.getConsultationId());
    }
  
}