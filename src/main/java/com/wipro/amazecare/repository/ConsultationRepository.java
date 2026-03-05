package com.wipro.amazecare.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wipro.amazecare.entity.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    // Get consultations by patient
    List<Consultation> findByPatient_PatientId(Long patientId);

    // Get consultations by doctor
    List<Consultation> findByDoctor_DoctorId(Long doctorId);

    // Total consultations of a doctor
    Long countByDoctor_DoctorId(Long doctorId);

    // Month wise consultation count
    @Query("SELECT COUNT(c) FROM Consultation c WHERE MONTH(c.consultationDate) = :month")
    Long countByMonth(@Param("month") int month);

    // Doctor consultation count in a specific month
    @Query("SELECT COUNT(c) FROM Consultation c WHERE c.doctor.doctorId = :doctorId AND MONTH(c.consultationDate) = :month")
    Long countByDoctorAndMonth(@Param("doctorId") Long doctorId,
                               @Param("month") int month);


	    
	    @Query("SELECT COUNT(DISTINCT c.patient) FROM Consultation c")
	    Long countDistinctPatients();
	    
	    

}