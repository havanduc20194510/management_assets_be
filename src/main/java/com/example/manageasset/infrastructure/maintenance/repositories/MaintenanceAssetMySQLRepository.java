package com.example.manageasset.infrastructure.maintenance.repositories;

import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MaintenanceAssetMySQLRepository implements MaintenanceAssetRepository {
    private final MaintenanceAssetJpa maintenanceAssetJpa;

    @Override
    public void deleteAllByMaintenanceId(String maintenanceId) {
        maintenanceAssetJpa.deleteAllByMaintenanceAssetLeased_Id(maintenanceId);
    }

    @Override
    public boolean existedMaintenanceAssetLeased(Long id) {
        return maintenanceAssetJpa.existedMaintenanceAssetLeased(id);
    }

}
