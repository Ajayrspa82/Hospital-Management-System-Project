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

import com.wipro.amazecare.dto.MedicalTestDto;
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
class MedicalTestServiceTest {

    @Autowired
    private MedicalTestService medicalTestService;

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

        Specialization specialization = new Specialization();
        specialization.setSpecializationName("Cardiology");
        specialization = specializationRepository.save(specialization);

        Doctor doctor = new Doctor();
        doctor.setName("Dr Test");
        doctor.setSpecialization(specialization);
        doctor = doctorRepository.save(doctor);

        Patient patient = new Patient();
        patient.setFullName("Test Patient");
        patient = patientRepository.save(patient);

        consultation = new Consultation();
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setSymptoms("Chest Pain");
        consultation.setConsultationDate(LocalDateTime.now());
        consultation = consultationRepository.save(consultation);
    }

    @Test
    void testCreateMedicalTest() {

        MedicalTestDto dto = new MedicalTestDto();
        dto.setConsultationId(consultation.getConsultationId());
        dto.setTestName("ECG");
        dto.setTestStatus("PENDING");
        dto.setDescription("Heart test");
        dto.setResult("Normal");

        MedicalTestDto result =
                medicalTestService.createMedicalTest(dto);

        assertNotNull(result);
        assertNotNull(result.getTestId());
        assertEquals("ECG", result.getTestName());
        assertEquals("PENDING", result.getTestStatus());
        assertEquals(consultation.getConsultationId(), result.getConsultationId());
    }

   
}