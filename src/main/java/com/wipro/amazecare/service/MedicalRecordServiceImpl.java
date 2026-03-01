package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.MedicalRecordDto;
import com.wipro.amazecare.entity.MedicalRecord;
import com.wipro.amazecare.repository.MedicalRecordRepository;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;

    public MedicalRecordServiceImpl(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public MedicalRecordDto createRecord(MedicalRecordDto dto) {
        MedicalRecord record = new MedicalRecord();
        record.setPatientId(dto.getPatientId());
        record.setConsultationId(dto.getConsultationId());
        record.setNotes(dto.getNotes());

        repository.save(record);
        dto.setRecordId(record.getRecordId());
        return dto;
    }

    @Override
    public List<MedicalRecordDto> getRecordsByPatient(Long patientId) {
        return repository.findByPatientId(patientId).stream().map(r -> {
            MedicalRecordDto dto = new MedicalRecordDto();
            dto.setRecordId(r.getRecordId());
            dto.setPatientId(r.getPatientId());
            dto.setConsultationId(r.getConsultationId());
            dto.setNotes(r.getNotes());
            return dto;
        }).collect(Collectors.toList());
    }
}