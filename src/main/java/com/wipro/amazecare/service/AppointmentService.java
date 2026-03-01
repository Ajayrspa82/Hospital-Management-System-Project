package com.wipro.amazecare.service;





import java.util.List;
import com.wipro.amazecare.dto.*;

public interface AppointmentService {

    AppointmentResponseDto bookAppointment(AppointmentRequestDto dto);

    AppointmentResponseDto cancelAppointment(Long id);

    AppointmentResponseDto rescheduleAppointment(Long id, AppointmentUpdateDto dto);

    AppointmentResponseDto approveAppointment(Long id);

    AppointmentResponseDto rejectAppointment(Long id);

    AppointmentResponseDto getAppointmentById(Long id);

    List<AppointmentResponseDto> getAllAppointments();
}