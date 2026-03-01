package com.wipro.amazecare.service;



import com.wipro.amazecare.dto.*;
import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto bookAppointment(AppointmentRequestDto request);

    AppointmentResponseDto getAppointmentById(Long appointmentId);

    List<AppointmentResponseDto> getAllAppointments();

    AppointmentResponseDto cancelAppointment(Long appointmentId);

    AppointmentResponseDto rescheduleAppointment(Long appointmentId, AppointmentUpdateDto dto);

    AppointmentResponseDto approveAppointment(Long appointmentId);

    AppointmentResponseDto rejectAppointment(Long appointmentId);
}