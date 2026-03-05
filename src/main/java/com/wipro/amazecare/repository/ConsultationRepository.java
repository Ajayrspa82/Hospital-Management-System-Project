package com.wipro.amazecare.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wipro.amazecare.entity.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    
	
	 List<Consultation> findByPatient_PatientId(Long patientId);

	    List<Consultation> findByDoctor_DoctorId(Long doctorId);

	    Long countByConsultationDateBetween(LocalDate start, LocalDate end);

	    Long countByDoctor_DoctorId(Long doctorId);

	    
	    @Query("SELECT COUNT(DISTINCT c.patient) FROM Consultation c")
	    Long countDistinctPatients();
	    
	    
}