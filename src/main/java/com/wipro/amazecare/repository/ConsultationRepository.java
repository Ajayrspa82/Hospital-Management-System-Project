package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}