package com.example.manageasset.infrastructure.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<UserEntity, Long> {
}
