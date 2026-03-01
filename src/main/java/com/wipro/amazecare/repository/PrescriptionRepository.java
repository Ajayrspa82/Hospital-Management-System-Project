package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.Prescription;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByConsultationId(Long consultationId);
}