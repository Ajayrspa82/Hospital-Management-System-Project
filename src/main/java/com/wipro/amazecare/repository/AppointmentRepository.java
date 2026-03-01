package com.wipro.amazecare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {}