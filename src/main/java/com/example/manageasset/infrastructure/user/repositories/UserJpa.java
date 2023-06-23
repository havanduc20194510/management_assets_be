package com.example.manageasset.infrastructure.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserJpa extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByIdAndIsDeletedFalse(Long id);
    UserEntity findByUsernameAndIsDeletedFalse(String username);
}
