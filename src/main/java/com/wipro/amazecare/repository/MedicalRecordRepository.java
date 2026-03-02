package com.wipro.amazecare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wipro.amazecare.entity.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient_PatientId(Long patientId);

    @Query("SELECT m FROM MedicalRecord m WHERE m.patient.patientId = :patientId ORDER BY m.recordId DESC")
    Optional<MedicalRecord> findLatestRecordByPatientId(@Param("patientId") Long patientId);
}