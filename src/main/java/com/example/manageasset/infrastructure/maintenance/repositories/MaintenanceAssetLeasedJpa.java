package com.example.manageasset.infrastructure.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceAssetLeasedJpa extends JpaRepository<MaintenanceAssetLeasedEntity, String> {
}
