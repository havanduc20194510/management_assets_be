package com.example.manageasset.domain.user.repositories;

import com.example.manageasset.domain.user.models.Department;

import java.util.List;

public interface DepartmentRepository {
    Department findById(Long id);
    void save(Department department);
    List<Department> list();
}
