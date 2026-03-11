package com.wipro.amazecare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.amazecare.dto.DoctorDto;
import com.wipro.amazecare.entity.Doctor;
import com.wipro.amazecare.entity.Specialization;
import com.wipro.amazecare.repository.DoctorRepository;
import com.wipro.amazecare.repository.SpecializationRepository;
import com.wipro.amazecare.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public DoctorDto createDoctor(DoctorDto dto) {

        Specialization specialization = specializationRepository
                .findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        Doctor doctor = mapToEntity(dto);
        doctor.setSpecialization(specialization);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToDto(savedDoctor);
    }

    @Override
    public DoctorDto getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));

        return mapToDto(doctor);
    }

    @Override
    public List<DoctorDto> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto updateDoctor(Long id, DoctorDto dto) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));

        Specialization specialization = specializationRepository
                .findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        doctor.setName(dto.getName());
        doctor.setQualification(dto.getQualification());
        doctor.setExperience(dto.getExperience());
        doctor.setDesignation(dto.getDesignation());
        doctor.setSpecialization(specialization);

        Doctor updated = doctorRepository.save(doctor);

        return mapToDto(updated);
    }

    @Override
    public void deleteDoctor(Long id) {

        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with id " + id);
        }

        doctorRepository.deleteById(id);
    }

    // ===== Mapping Methods =====

    private DoctorDto mapToDto(Doctor doctor) {

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

    private Doctor mapToEntity(DoctorDto dto) {

        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setQualification(dto.getQualification());
        doctor.setExperience(dto.getExperience());
        doctor.setDesignation(dto.getDesignation());
        return doctor;
    }

	@Override
	public Object addDoctor(DoctorDto doctorDto) {
		// TODO Auto-generated method stub
		return null;
	}
}