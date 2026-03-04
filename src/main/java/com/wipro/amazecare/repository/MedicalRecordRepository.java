package com.wipro.amazecare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.amazecare.entity.MedicalRecord;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient_PatientId(Long patientId);

    Optional<MedicalRecord> findByConsultation_ConsultationId(Long consultationId);

    @Query("SELECT m FROM MedicalRecord m WHERE m.patient.patientId = :patientId ORDER BY m.recordId DESC")
    List<MedicalRecord> findRecordsByPatientOrderByDesc(Long patientId);  
    Optional<MedicalRecord> findLatestRecordByPatient_PatientId(Long patientId);
}