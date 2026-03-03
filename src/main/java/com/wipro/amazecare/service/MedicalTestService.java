package com.wipro.amazecare.service;

import com.wipro.amazecare.dto.MedicalTestDto;
import java.util.List;

public interface MedicalTestService {

    MedicalTestDto createMedicalTest(MedicalTestDto dto);

    MedicalTestDto getMedicalTestById(Long testId);

    List<MedicalTestDto> getByConsultation(Long consultationId);

    MedicalTestDto updateMedicalTest(Long testId, MedicalTestDto dto);

    void deleteMedicalTest(Long testId);
}