package com.example.manageasset.infrastructure.lease.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetLeasedJpa extends JpaRepository<AssetLeasedEntity, Long> {
}
