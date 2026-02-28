package com.wipro.amazecare.service;

import java.util.List;

import com.wipro.amazecare.dto.PatientDto;

public interface PatientService {

    PatientDto createPatient(PatientDto patientDto);

    PatientDto getPatientById(Long patientId);

    List<PatientDto> getAllPatients();

    PatientDto updatePatient(Long patientId, PatientDto patientDto);

    void deletePatient(Long patientId);
}