package com.example.manageasset.domain.maintenance.repositories;

public interface MaintenanceAssetRepository {
    void deleteAllByMaintenanceId(String maintenanceId);
}
