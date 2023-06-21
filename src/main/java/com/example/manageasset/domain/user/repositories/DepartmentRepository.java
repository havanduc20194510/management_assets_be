package com.example.manageasset.domain.user.repositories;

import com.example.manageasset.domain.user.models.Department;

public interface DepartmentRepository {
    Department findById(Long id);
}
