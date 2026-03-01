package com.wipro.amazecare.service;

import java.util.List;
import com.wipro.amazecare.dto.MedicalRecordDto;

public interface MedicalRecordService {

    MedicalRecordDto createRecord(MedicalRecordDto dto);
    List<MedicalRecordDto> getRecordsByPatient(Long patientId);
}