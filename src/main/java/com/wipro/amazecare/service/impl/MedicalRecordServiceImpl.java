package com.wipro.amazecare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.MedicalRecordDto;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.entity.MedicalRecord;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.exception.ResourceNotFoundException;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.MedicalRecordRepository;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.service.MedicalRecordService;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public MedicalRecordDto createRecord(MedicalRecordDto dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Consultation not found with id: " + dto.getConsultationId()));

        MedicalRecord record = new MedicalRecord();
        record.setPatient(patient);
        record.setConsultation(consultation);
        record.setNotes(dto.getNotes());

        repository.save(record);

        return mapToDto(record);
    }

    @Override
    public List<MedicalRecordDto> getRecordsByPatient(Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }

        return repository.findByPatient_PatientId(patientId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalRecordDto getMedicalRecordByPatient(Long patientId) {

        List<MedicalRecord> records =
                repository.findRecordsByPatientOrderByDesc(patientId);

        if (records.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Medical record not found for patient id: " + patientId);
        }

        return mapToDto(records.get(0)); // latest record
    }

    @Override
    public MedicalRecordDto getRecordByConsultation(Long consultationId) {

        MedicalRecord record = repository.findByConsultation_ConsultationId(consultationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medical record not found for consultation id: " + consultationId));

        return mapToDto(record);
    }

    @Override
    public MedicalRecordDto updateRecord(Long recordId, MedicalRecordDto dto) {

        MedicalRecord record = repository.findById(recordId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medical record not found with id: " + recordId));

        if (dto.getNotes() != null && !dto.getNotes().isBlank()) {
            record.setNotes(dto.getNotes());
        }

        repository.save(record);

        return mapToDto(record);
    }

    @Override
    public void deleteRecord(Long recordId) {

        MedicalRecord record = repository.findById(recordId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Medical record not found with id: " + recordId));

        repository.delete(record);
    }

    private MedicalRecordDto mapToDto(MedicalRecord r) {

        MedicalRecordDto dto = new MedicalRecordDto();
        dto.setRecordId(r.getRecordId());
        dto.setPatientId(r.getPatient().getPatientId());
        dto.setConsultationId(r.getConsultation().getConsultationId());
        dto.setNotes(r.getNotes());

        return dto;
    }
}