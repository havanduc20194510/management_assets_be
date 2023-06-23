package com.example.manageasset.domain.user.services.department;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateDepartmentService {
    private final DepartmentRepository departmentRepository;

    public UpdateDepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void update(DepartmentDto departmentDto) throws NotFoundException {
        Department department = departmentRepository.findById(departmentDto.getId());
        if(department == null) throw new NotFoundException(String.format("Department[id=%d] not found", departmentDto.getId()));
        department.update(departmentDto.getName());
        departmentRepository.save(department);
    }
}
