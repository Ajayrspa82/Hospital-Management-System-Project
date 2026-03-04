package com.wipro.amazecare.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.amazecare.dto.AppointmentRequestDto;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.exception.AppointmentNotFoundException;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.repository.SpecializationRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Test
    void testBookAppointment_DoctorNotFound() {

        Patient patient = new Patient();
        patient.setFullName("Patient 3");
        patient.setEmail("patient3@test.com");
        patient.setDateOfBirth(LocalDate.of(1992, 3, 3));
        patient.setGender("Male");
        patient.setMobileNumber("7777777777");
        patient = patientRepository.save(patient);

        AppointmentRequestDto request = new AppointmentRequestDto();
        request.setPatientId(patient.getPatientId());
        request.setDoctorId(999L); // invalid
        request.setReason("Fever");
        request.setAppointmentDate(LocalDateTime.now().plusDays(1));

        assertThrows(AppointmentNotFoundException.class, () -> {
            appointmentService.bookAppointment(request);
        });
    }

}