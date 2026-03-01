package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

	List<MedicalRecord> findByPatient_PatientId(Long patientId);
}