package com.wipro.amazecare.repository;



import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.*;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorAndAppointmentDate(
            Doctor doctor,
            LocalDateTime appointmentDate
    );
}