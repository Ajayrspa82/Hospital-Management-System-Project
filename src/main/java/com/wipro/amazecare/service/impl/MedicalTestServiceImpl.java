package com.wipro.amazecare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.MedicalTestDto;
import com.wipro.amazecare.entity.Consultation;
import com.wipro.amazecare.entity.MedicalTest;
import com.wipro.amazecare.exception.ResourceNotFoundException;
import com.wipro.amazecare.repository.ConsultationRepository;
import com.wipro.amazecare.repository.MedicalTestRepository;
import com.wipro.amazecare.service.MedicalTestService;

@Service
public class MedicalTestServiceImpl implements MedicalTestService {

    @Autowired
    private MedicalTestRepository medicalTestRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public MedicalTestDto createMedicalTest(MedicalTestDto dto) {

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Consultation not found with id: " + dto.getConsultationId()));

        MedicalTest test = new MedicalTest();
        test.setConsultation(consultation);
        test.setTestName(dto.getTestName());
        test.setTestStatus(dto.getTestStatus());
        test.setDescription(dto.getDescription());
        test.setResult(dto.getResult());

        medicalTestRepository.save(test);

        return mapToDto(test);
    }

    @Override
    public MedicalTestDto getMedicalTestById(Long testId) {

        MedicalTest test = medicalTestRepository.findById(testId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medical test not found with id: " + testId));

        return mapToDto(test);
    }

    @Override
    public List<MedicalTestDto> getByConsultation(Long consultationId) {

        if (!consultationRepository.existsById(consultationId)) {
            throw new ResourceNotFoundException(
                    "Consultation not found with id: " + consultationId);
        }

        return medicalTestRepository
                .findByConsultation_ConsultationId(consultationId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalTestDto updateMedicalTest(Long testId, MedicalTestDto dto) {

        MedicalTest test = medicalTestRepository.findById(testId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medical test not found with id: " + testId));

        if (dto.getTestName() != null && !dto.getTestName().isBlank())
            test.setTestName(dto.getTestName());

        if (dto.getTestStatus() != null && !dto.getTestStatus().isBlank())
            test.setTestStatus(dto.getTestStatus());

        if (dto.getDescription() != null)
            test.setDescription(dto.getDescription());

        if (dto.getResult() != null)
            test.setResult(dto.getResult());

        medicalTestRepository.save(test);

        return mapToDto(test);
    }

    @Override
    public void deleteMedicalTest(Long testId) {

        MedicalTest test = medicalTestRepository.findById(testId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medical test not found with id: " + testId));

        medicalTestRepository.delete(test);
    }

    private MedicalTestDto mapToDto(MedicalTest test) {

        MedicalTestDto dto = new MedicalTestDto();
        dto.setTestId(test.getTestId());
        dto.setConsultationId(test.getConsultation().getConsultationId());
        dto.setTestName(test.getTestName());
        dto.setTestStatus(test.getTestStatus());
        dto.setDescription(test.getDescription());
        dto.setResult(test.getResult());

        return dto;
    }
}