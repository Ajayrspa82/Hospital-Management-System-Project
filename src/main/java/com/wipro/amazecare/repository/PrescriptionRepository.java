package com.wipro.amazecare.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByConsultation_ConsultationId(Long consultationId);
}