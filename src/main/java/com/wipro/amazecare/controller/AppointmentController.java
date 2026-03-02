package com.wipro.amazecare.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.wipro.amazecare.dto.AppointmentRequestDto;
import com.wipro.amazecare.dto.AppointmentResponseDto;
import com.wipro.amazecare.dto.AppointmentUpdateDto;
import com.wipro.amazecare.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // BOOK APPOINTMENT
    @PostMapping("/book")
    public ResponseEntity<?> book(@Valid @RequestBody AppointmentRequestDto dto,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }

        return ResponseEntity.ok(appointmentService.bookAppointment(dto));
    }

    // CANCEL APPOINTMENT
    @PutMapping("/cancel/{id}")
    public ResponseEntity<AppointmentResponseDto> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    // RESCHEDULE APPOINTMENT
    @PutMapping("/reschedule/{id}")
    public ResponseEntity<?> reschedule(@PathVariable Long id,
                                        @Valid @RequestBody AppointmentUpdateDto dto,
                                        BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList()));
        }

        return ResponseEntity.ok(appointmentService.rescheduleAppointment(id, dto));
    }

    // APPROVE APPOINTMENT
    @PutMapping("/approve/{id}")
    public ResponseEntity<AppointmentResponseDto> approve(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.approveAppointment(id));
    }

    // REJECT APPOINTMENT
    @PutMapping("/reject/{id}")
    public ResponseEntity<AppointmentResponseDto> reject(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.rejectAppointment(id));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAll() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
}