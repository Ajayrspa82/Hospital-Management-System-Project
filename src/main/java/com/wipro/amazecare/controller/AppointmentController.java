package com.wipro.amazecare.controller;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wipro.amazecare.dto.*;
import com.wipro.amazecare.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDto> book(@RequestBody AppointmentRequestDto dto) {
        return ResponseEntity.ok(appointmentService.bookAppointment(dto));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<AppointmentResponseDto> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    @PutMapping("/reschedule/{id}")
    public ResponseEntity<AppointmentResponseDto> reschedule(
            @PathVariable Long id,
            @RequestBody AppointmentUpdateDto dto) {
        return ResponseEntity.ok(appointmentService.rescheduleAppointment(id, dto));
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<AppointmentResponseDto> approve(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.approveAppointment(id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<AppointmentResponseDto> reject(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.rejectAppointment(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAll() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
}