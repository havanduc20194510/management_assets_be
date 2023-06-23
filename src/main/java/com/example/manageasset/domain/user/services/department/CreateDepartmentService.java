package com.example.manageasset.domain.user.services.department;

import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateDepartmentService {
    private final DepartmentRepository departmentRepository;

    public CreateDepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void create(DepartmentDto departmentDto){
        Department department = Department.create(departmentDto.getName());
        departmentRepository.save(department);
    }
}
