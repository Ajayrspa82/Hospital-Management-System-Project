package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.amazecare.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}