package com.example.manageasset.infrastructure.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentJpa extends JpaRepository<DepartmentEntity, Long>, JpaSpecificationExecutor<DepartmentEntity> {
    DepartmentEntity findByIdAndIsDeletedFalse(Long id);
}
