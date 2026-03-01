package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.amazecare.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}