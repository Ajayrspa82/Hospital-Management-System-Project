package com.wipro.amazecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.amazecare.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}