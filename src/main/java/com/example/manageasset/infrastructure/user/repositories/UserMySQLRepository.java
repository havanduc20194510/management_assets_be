package com.example.manageasset.infrastructure.user.repositories;

import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
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
public class UserMySQLRepository implements UserRepository {
    private final UserJpa userJpa;

    public UserMySQLRepository(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    @Override
    public void save(User user) {
        userJpa.save(UserEntity.fromModel(user));
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userJpa.findByIdAndIsDeletedFalse(id);
        return userEntity == null ? null : userEntity.toModel();
    }

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userJpa.findByUsernameAndIsDeletedFalse(username);
        return userEntity == null ? null : userEntity.toModel();
    }

    @Override
    public List<User> list(String key, QueryFilter filter, Long departmentId) {
        Specification<UserEntity> specificationOr = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    criteriaBuilder.like(root.get("fullName"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("mobile"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("address"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("email"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("username"), "%"+key+"%")
            );

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });

        Specification<UserEntity> specificationAnd = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    criteriaBuilder.equal(root.get("isDeleted"), false)
            );

            if(departmentId !=null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("departmentEntity").get("id"), departmentId)
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getLimit(),
                filter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());

        Specification<UserEntity> conditions = Specification.where(specificationAnd);
        if(!Strings.isNullOrEmpty(key)) {
            conditions = conditions.and(specificationOr);
        }
        return userJpa.findAll(conditions, pageable).getContent()
                .stream().map(UserEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public long totalList(String key, Long departmentId) {
        Specification<UserEntity> specificationOr = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    criteriaBuilder.like(root.get("fullName"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("mobile"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("address"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("email"), "%"+key+"%")
            );
            predicates.add(
                    criteriaBuilder.like(root.get("username"), "%"+key+"%")
            );

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });

        Specification<UserEntity> specificationAnd = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    criteriaBuilder.equal(root.get("isDeleted"), false)
            );

            if(departmentId !=null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("departmentEntity").get("id"), departmentId)
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        Specification<UserEntity> conditions = Specification.where(specificationAnd);
        if(!Strings.isNullOrEmpty(key)) {
            conditions = conditions.and(specificationOr);
        }
        return userJpa.count(conditions);
    }
}
