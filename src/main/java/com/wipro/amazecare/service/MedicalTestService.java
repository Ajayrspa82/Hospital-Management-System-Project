package com.wipro.amazecare.service;

import com.wipro.amazecare.dto.MedicalTestDto;
import java.util.List;

public interface MedicalTestService {

    MedicalTestDto createMedicalTest(MedicalTestDto medicalTestDto);

    MedicalTestDto getMedicalTestById(Long testId);
    
    MedicalTestDto addMedicalTest(MedicalTestDto dto);

    List<MedicalTestDto> getByConsultation(Long consultationId);

    List<MedicalTestDto> getTestsByConsultationId(Long consultationId);

    MedicalTestDto updateMedicalTest(Long testId, MedicalTestDto medicalTestDto);

    void deleteMedicalTest(Long testId);
}