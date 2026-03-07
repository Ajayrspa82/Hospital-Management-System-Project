package com.wipro.amazecare.service;

import java.util.List;

import com.wipro.amazecare.dto.DoctorDto;

public interface DoctorService {

    DoctorDto createDoctor(DoctorDto doctorDto);
    DoctorDto getDoctorById(Long doctorId);
    List<DoctorDto> getAllDoctors();
    DoctorDto updateDoctor(Long doctorId, DoctorDto doctorDto);
    void deleteDoctor(Long doctorId);
	Object addDoctor(DoctorDto doctorDto);
}