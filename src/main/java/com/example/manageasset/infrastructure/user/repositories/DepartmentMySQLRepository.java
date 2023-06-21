package com.example.manageasset.infrastructure.user.repositories;

import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.repositories.DepartmentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentMySQLRepository implements DepartmentRepository {
    private final DepartmentJpa departmentJpa;

    public DepartmentMySQLRepository(DepartmentJpa departmentJpa) {
        this.departmentJpa = departmentJpa;
    }

    @Override
    public Department findById(Long id) {
        DepartmentEntity departmentEntity = departmentJpa.findByIdAndIsDeletedFalse(id);
        return departmentEntity == null ? null : departmentEntity.toModel();
    }
}
