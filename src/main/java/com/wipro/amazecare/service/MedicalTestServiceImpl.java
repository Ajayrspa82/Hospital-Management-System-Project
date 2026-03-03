package com.wipro.amazecare.service;

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
@Service
public class MedicalTestServiceImpl implements MedicalTestService {

    @Autowired
    private MedicalTestRepository medicalTestRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public MedicalTestDto addMedicalTest(MedicalTestDto dto) {

        Consultation consultation = consultationRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consultation not found with id: " + dto.getConsultationId()));

        MedicalTest test = new MedicalTest();
        test.setConsultation(consultation);
        test.setTestName(dto.getTestName());
        test.setTestStatus(dto.getTestStatus());

        medicalTestRepository.save(test);

        dto.setTestId(test.getTestId());
        return dto;
    }

    @Override
    public List<MedicalTestDto> getByConsultation(Long consultationId) {

        List<MedicalTest> tests = medicalTestRepository
                .findByConsultation_ConsultationId(consultationId);

        return tests.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalTestDto updateMedicalTest(Long id, MedicalTestDto dto) {

        MedicalTest test = medicalTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medical test not found with id: " + id));

        if (dto.getTestName() != null) test.setTestName(dto.getTestName());
        if (dto.getTestStatus() != null) test.setTestStatus(dto.getTestStatus());

        medicalTestRepository.save(test);

        return mapToDto(test);
    }

    @Override
    public void deleteMedicalTest(Long id) {
        MedicalTest test = medicalTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medical test not found with id: " + id));

        medicalTestRepository.delete(test);
    }

    private MedicalTestDto mapToDto(MedicalTest test) {
        MedicalTestDto dto = new MedicalTestDto();
        dto.setTestId(test.getTestId());
        dto.setConsultationId(test.getConsultation().getConsultationId());
        dto.setTestName(test.getTestName());
        dto.setTestStatus(test.getTestStatus());
        return dto;
    }

	@Override
	public MedicalTestDto createMedicalTest(MedicalTestDto medicalTestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedicalTestDto getMedicalTestById(Long testId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MedicalTestDto> getTestsByConsultationId(Long consultationId) {
		// TODO Auto-generated method stub
		return null;
	}
}