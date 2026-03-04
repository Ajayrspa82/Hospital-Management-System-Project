package com.wipro.amazecare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.PatientDto;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public PatientDto createPatient(PatientDto dto) {

        Patient patient = mapToEntity(dto);
        Patient savedPatient = patientRepository.save(patient);

        return mapToDto(savedPatient);
    }

    @Override
    public PatientDto getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + id));

        return mapToDto(patient);
    }

    @Override
    public List<PatientDto> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto updatePatient(Long id, PatientDto dto) {

        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + id));

        existing.setFullName(dto.getFullName());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setGender(dto.getGender());
        existing.setMobileNumber(dto.getMobileNumber());
        existing.setEmail(dto.getEmail());
        existing.setMedicalHistory(dto.getMedicalHistory());

        Patient updated = patientRepository.save(existing);

        return mapToDto(updated);
    }

    @Override
    public void deletePatient(Long id) {

        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id " + id);
        }

        patientRepository.deleteById(id);
    }

    // ===== Mapping Methods =====

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

    private Patient mapToEntity(PatientDto dto) {
        Patient patient = new Patient();
        patient.setFullName(dto.getFullName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setMobileNumber(dto.getMobileNumber());
        patient.setEmail(dto.getEmail());
        patient.setMedicalHistory(dto.getMedicalHistory());
        return patient;
    }
}