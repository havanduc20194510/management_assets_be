package com.example.manageasset.infrastructure.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpa extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByIdAndIsDeletedFalse(Long id);
}
