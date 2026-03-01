package com.wipro.amazecare.service;

import java.util.List;
import com.wipro.amazecare.dto.PrescriptionDto;

public interface PrescriptionService {

    PrescriptionDto addPrescription(PrescriptionDto dto);
    List<PrescriptionDto> getPrescriptionsByConsultation(Long consultationId);
}