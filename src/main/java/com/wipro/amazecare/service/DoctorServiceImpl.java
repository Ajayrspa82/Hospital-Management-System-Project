package com.wipro.amazecare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.DoctorDto;
import com.wipro.amazecare.entity.Doctor;
import com.wipro.amazecare.entity.Specialization;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.SpecializationRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             SpecializationRepository specializationRepository) {
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
    }

    @Override
    public DoctorDto createDoctor(DoctorDto dto) {
        Specialization specialization =
                specializationRepository.findById(dto.getSpecializationId()).orElseThrow();

        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setQualification(dto.getQualification());
        doctor.setExperience(dto.getExperience());
        doctor.setDesignation(dto.getDesignation());
        doctor.setSpecialization(specialization);

        doctorRepository.save(doctor);
        dto.setDoctorId(doctor.getDoctorId());
        return dto;
    }

    @Override
    public DoctorDto getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        DoctorDto dto = new DoctorDto();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setName(doctor.getName());
        dto.setQualification(doctor.getQualification());
        dto.setExperience(doctor.getExperience());
        dto.setDesignation(doctor.getDesignation());
        dto.setSpecializationId(doctor.getSpecialization().getSpecializationId());
        dto.setSpecializationName(doctor.getSpecialization().getSpecializationName());
        return dto;
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(d -> getDoctorById(d.getDoctorId()))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto updateDoctor(Long doctorId, DoctorDto dto) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        doctor.setName(dto.getName());
        doctor.setQualification(dto.getQualification());
        doctor.setExperience(dto.getExperience());
        doctor.setDesignation(dto.getDesignation());
        doctorRepository.save(doctor);
        return dto;
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }
}