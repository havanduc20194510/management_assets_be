package com.example.manageasset.domain.user.services.department;

import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListDepartmentService {
    private final DepartmentRepository departmentRepository;

    public ListDepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public PagingPayload<List<DepartmentDto>> list(String key, Integer page, Integer limit, String sort){
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<Department> departments = departmentRepository.list(key, filter);
        List<DepartmentDto> departmentDtos = departments.stream().map(DepartmentDto::fromModel).collect(Collectors.toList());
        PagingPayload.PagingPayloadBuilder<List<DepartmentDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(departmentDtos);
        payloadBuilder.page(page);
        payloadBuilder.total(departmentRepository.totalList(key));
        payloadBuilder.limit(limit);
        return payloadBuilder.build();
    }
}
