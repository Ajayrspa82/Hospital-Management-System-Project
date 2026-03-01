package com.wipro.amazecare.service;

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
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found"));

        MedicalRecord record = new MedicalRecord();
        record.setPatient(patient);
        record.setConsultation(consultation);
        record.setNotes(dto.getNotes());

        repository.save(record);

        dto.setRecordId(record.getRecordId());
        return dto;
    }

    @Override
    public List<MedicalRecordDto> getRecordsByPatient(Long patientId) {

        return repository.findByPatient_PatientId(patientId)
                .stream()
                .map(r -> {
                    MedicalRecordDto dto = new MedicalRecordDto();

                    dto.setRecordId(r.getRecordId());
                    dto.setPatientId(r.getPatient().getPatientId());
                    dto.setConsultationId(r.getConsultation().getConsultationId());
                    dto.setNotes(r.getNotes());

                    return dto;
                })
                .collect(Collectors.toList());
    }
}