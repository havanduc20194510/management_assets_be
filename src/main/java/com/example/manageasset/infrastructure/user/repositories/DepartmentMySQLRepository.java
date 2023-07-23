package com.example.manageasset.infrastructure.user.repositories;

import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.repositories.DepartmentRepository;
import com.google.common.base.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void save(Department department) {
        departmentJpa.save(DepartmentEntity.fromModel(department));
    }

    @Override
    public List<Department> list(String key, QueryFilter queryFilter) {
        Specification<DepartmentEntity> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!Strings.isNullOrEmpty(key)) {
                predicates.add(
                        criteriaBuilder.like(root.get("name"), "%"+key+"%")
                );
            }

            predicates.add(
                    criteriaBuilder.equal(root.get("isDeleted"), false)
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        Pageable pageable = PageRequest.of(queryFilter.getPage(), queryFilter.getLimit(),
                queryFilter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());
        return departmentJpa.findAll(specification, pageable).getContent()
                .stream().map(DepartmentEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public long totalList(String key) {
        Specification<DepartmentEntity> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!Strings.isNullOrEmpty(key)) {
                predicates.add(
                        criteriaBuilder.like(root.get("name"), "%"+key+"%")
                );
            }

            predicates.add(
                    criteriaBuilder.equal(root.get("isDeleted"), false)
            );

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return departmentJpa.count(specification);
    }
}
