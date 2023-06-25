package com.example.manageasset.infrastructure.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceAssetJpa extends JpaRepository<MaintenanceAssetEntity, MaintenanceAssetLeasedKey> {
    void deleteAllByMaintenanceAssetLeased_Id(String maintenanceId);
}
