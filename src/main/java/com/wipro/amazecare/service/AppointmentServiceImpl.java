package com.wipro.amazecare.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.AppointmentRequestDto;
import com.wipro.amazecare.dto.AppointmentResponseDto;
import com.wipro.amazecare.dto.AppointmentUpdateDto;
import com.wipro.amazecare.entity.Appointment;
import com.wipro.amazecare.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public AppointmentResponseDto bookAppointment(AppointmentRequestDto request) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());
        appointment.setStatus("BOOKED");
        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());

        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return mapToResponse(appointment);
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponseDto cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("CANCELLED");
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto rescheduleAppointment(Long appointmentId, AppointmentUpdateDto dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setAppointmentDate(dto.getNewDate());
        appointment.setStatus("RESCHEDULED");
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto approveAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("APPROVED");
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto rejectAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("REJECTED");
        return mapToResponse(appointmentRepository.save(appointment));
    }

    // Mapping helper
    private AppointmentResponseDto mapToResponse(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus());
        dto.setPatientId(appointment.getPatientId());
        dto.setDoctorId(appointment.getDoctorId());
        return dto;
    }
   
}