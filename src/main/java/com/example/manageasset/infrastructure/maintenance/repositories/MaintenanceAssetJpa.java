package com.example.manageasset.infrastructure.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaintenanceAssetJpa extends JpaRepository<MaintenanceAssetEntity, MaintenanceAssetLeasedKey> {
    void deleteAllByMaintenanceAssetLeased_Id(String maintenanceId);
    @Query("SELECT COUNT(m)>0 FROM MaintenanceAssetEntity m WHERE m.id.assetCode = :assetCode")
    Boolean existedMaintenanceAssetLeased(@Param("assetCode") String assetCode);
}
