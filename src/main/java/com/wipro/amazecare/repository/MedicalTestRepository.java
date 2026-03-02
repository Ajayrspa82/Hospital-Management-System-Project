package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.MedicalTest;

import java.util.List;

public interface MedicalTestRepository extends JpaRepository<MedicalTest, Long> {

    // Use property traversal to fetch by consultationId
    List<MedicalTest> findByConsultation_ConsultationId(Long consultationId);
}