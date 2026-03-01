package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.amazecare.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}