package com.wipro.amazecare.service;

import java.util.List;
import com.wipro.amazecare.dto.MedicalRecordDto;
import com.wipro.amazecare.dto.MedicalTestDto;

public interface MedicalRecordService {

    MedicalRecordDto createRecord(MedicalRecordDto dto);
    
    List<MedicalRecordDto> getRecordsByPatient(Long patientId);
    List<MedicalTestDto> getByConsultation(Long consultationId);
    MedicalRecordDto getMedicalRecordByPatient(Long patientId);


    MedicalRecordDto updateRecord(Long recordId, MedicalRecordDto dto);

    void deleteRecord(Long recordId);
}