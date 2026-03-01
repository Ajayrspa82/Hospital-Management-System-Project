package com.wipro.amazecare.service;

import com.wipro.amazecare.dto.MedicalTestDto;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.entity.MedicalTest;
import com.wipro.amazecare.repository.MedicalTestRepository;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.service.MedicalTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalTestServiceImpl implements MedicalTestService {

    @Autowired
    private MedicalTestRepository medicalTestRepository;

    @Autowired
    private ConsultationRepository consultationRepository; // Needed to fetch Consultation by ID

    
    private MedicalTestDto mapToDto(MedicalTest test) {
        MedicalTestDto dto = new MedicalTestDto();
        dto.setTestId(test.getTestId());
        dto.setConsultationId(test.getConsultation().getConsultationId());
        dto.setTestName(test.getTestName());
        dto.setDescription(test.getDescription());
        dto.setResult(test.getResult());
        return dto;
    }

    private MedicalTest mapToEntity(MedicalTestDto dto) {
        MedicalTest test = new MedicalTest();
        test.setTestName(dto.getTestName());
        test.setDescription(dto.getDescription());
        test.setResult(dto.getResult());

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new RuntimeException("Consultation not found with ID: " + dto.getConsultationId()));

        test.setConsultation(consultation);
        return test;
    }

    @Override
    public MedicalTestDto createMedicalTest(MedicalTestDto medicalTestDto) {
        MedicalTest saved = medicalTestRepository.save(mapToEntity(medicalTestDto));
        return mapToDto(saved);
    }

    @Override
    public MedicalTestDto getMedicalTestById(Long testId) {
        MedicalTest test = medicalTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("MedicalTest not found with ID: " + testId));
        return mapToDto(test);
    }

    @Override
    public List<MedicalTestDto> getTestsByConsultationId(Long consultationId) {
        List<MedicalTest> tests = medicalTestRepository.findByConsultation_ConsultationId(consultationId);
        return tests.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public MedicalTestDto updateMedicalTest(Long testId, MedicalTestDto dto) {
        MedicalTest test = medicalTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("MedicalTest not found with ID: " + testId));

        test.setTestName(dto.getTestName());
        test.setDescription(dto.getDescription());
        test.setResult(dto.getResult());

        if (!test.getConsultation().getConsultationId().equals(dto.getConsultationId())) {
            Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                    .orElseThrow(() -> new RuntimeException("Consultation not found with ID: " + dto.getConsultationId()));
            test.setConsultation(consultation);
        }

        MedicalTest updated = medicalTestRepository.save(test);
        return mapToDto(updated);
    }

    @Override
    public void deleteMedicalTest(Long testId) {
        MedicalTest test = medicalTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("MedicalTest not found with ID: " + testId));
        medicalTestRepository.delete(test);
    }
}