package com.wipro.amazecare.service;

import java.util.List;
import com.wipro.amazecare.dto.ConsultationDto;

public interface ConsultationService {

    ConsultationDto createConsultation(ConsultationDto dto);

    ConsultationDto getConsultationById(Long id);

    List<ConsultationDto> getAllConsultations();

    ConsultationDto updateConsultation(Long id, ConsultationDto dto);

    void deleteConsultation(Long id);
    
    List<ConsultationDto> getByPatient(Long patientId);

    List<ConsultationDto> getByDoctor(Long doctorId);
}