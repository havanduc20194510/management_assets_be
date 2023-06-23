package com.example.manageasset.domain.user.services.department;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteDepartmentService {
    private final DepartmentRepository departmentRepository;

    public DeleteDepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void delete(Long departmentId) throws NotFoundException {
        Department department = departmentRepository.findById(departmentId);
        if(department == null) throw new NotFoundException(String.format("Department[id=%d] not found", departmentId));
        department.delete();
        departmentRepository.save(department);
    }
}
