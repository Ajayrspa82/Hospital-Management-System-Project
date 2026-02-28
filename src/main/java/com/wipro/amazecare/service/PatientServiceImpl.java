package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.PatientDto;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Create Patient
    @Override
    public PatientDto createPatient(PatientDto dto) {

        Patient patient = new Patient();
        patient.setFullName(dto.getFullName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setMobileNumber(dto.getMobileNumber());
        patient.setEmail(dto.getEmail());
        patient.setMedicalHistory(dto.getMedicalHistory());

        Patient savedPatient = patientRepository.save(patient);

        return mapToDto(savedPatient);
    }

    // Get Patient By ID
    @Override
    public PatientDto getPatientById(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + patientId));

        return mapToDto(patient);
    }

    // Get All Patients
    @Override
    public List<PatientDto> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Update Patient
    @Override
    public PatientDto updatePatient(Long patientId, PatientDto dto) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + patientId));

        patient.setFullName(dto.getFullName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setMobileNumber(dto.getMobileNumber());
        patient.setEmail(dto.getEmail());
        patient.setMedicalHistory(dto.getMedicalHistory());

        Patient updatedPatient = patientRepository.save(patient);

        return mapToDto(updatedPatient);
    }

    // Delete Patient
    @Override
    public void deletePatient(Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient not found with id " + patientId);
        }

        patientRepository.deleteById(patientId);
    }

    // 🔥 Mapping Method (Important)
    private PatientDto mapToDto(Patient patient) {

        PatientDto dto = new PatientDto();
        dto.setPatientId(patient.getPatientId());
        dto.setFullName(patient.getFullName());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setGender(patient.getGender());
        dto.setMobileNumber(patient.getMobileNumber());
        dto.setEmail(patient.getEmail());
        dto.setMedicalHistory(patient.getMedicalHistory());

        return dto;
    }
}