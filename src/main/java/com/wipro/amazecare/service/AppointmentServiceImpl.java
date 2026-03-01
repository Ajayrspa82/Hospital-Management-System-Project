package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.AppointmentRequestDto;
import com.wipro.amazecare.dto.AppointmentResponseDto;
import com.wipro.amazecare.dto.AppointmentUpdateDto;
import com.wipro.amazecare.entity.Appointment;
import com.wipro.amazecare.entity.AppointmentStatus;
import com.wipro.amazecare.entity.Doctor;
import com.wipro.amazecare.entity.Patient;
import com.wipro.amazecare.exception.AppointmentAlreadyCancelledException;
import com.wipro.amazecare.exception.AppointmentNotFoundException;
import com.wipro.amazecare.exception.SlotNotAvailableException;
import com.wipro.amazecare.repository.AppointmentRepository;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.PatientRepository;
import com.wipro.amazecare.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public AppointmentResponseDto bookAppointment(AppointmentRequestDto dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new AppointmentNotFoundException("Doctor not found"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new AppointmentNotFoundException("Patient not found"));

        boolean slotAvailable = appointmentRepository
                .findByDoctorAndAppointmentDate(doctor, dto.getAppointmentDate())
                .isEmpty();

        if (!slotAvailable) {
            throw new SlotNotAvailableException("Selected slot is not available");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setReason(dto.getReason());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setCancelled(false);

        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto cancelAppointment(Long id) {
        Appointment appointment = getAppointment(id);

        if (appointment.isCancelled()) {
            throw new AppointmentAlreadyCancelledException("Appointment already cancelled");
        }

        appointment.setCancelled(true);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto rescheduleAppointment(Long id, AppointmentUpdateDto dto) {
        Appointment appointment = getAppointment(id);

        boolean slotAvailable = appointmentRepository
                .findByDoctorAndAppointmentDate(appointment.getDoctor(), dto.getNewDate())
                .isEmpty();

        if (!slotAvailable) {
            throw new SlotNotAvailableException("Selected slot is not available");
        }

        appointment.setAppointmentDate(dto.getNewDate());
        appointment.setStatus(AppointmentStatus.RESCHEDULED);

        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto approveAppointment(Long id) {
        Appointment appointment = getAppointment(id);
        appointment.setStatus(AppointmentStatus.APPROVED);
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto rejectAppointment(Long id) {
        Appointment appointment = getAppointment(id);
        appointment.setStatus(AppointmentStatus.REJECTED);
        return mapToResponse(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentResponseDto getAppointmentById(Long id) {
        return mapToResponse(getAppointment(id));
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    private Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
    }

    private AppointmentResponseDto mapToResponse(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus().name());
        dto.setDoctorId(appointment.getDoctor().getDoctorId());
        dto.setPatientId(appointment.getPatient().getPatientId());
        return dto;
    }
}