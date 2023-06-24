package com.example.manageasset.domain.maintenance.repositories;

import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.shared.models.QueryFilter;

import java.util.List;

public interface MaintenanceAssetLeasedRepository {
    void save(MaintenanceAssetLeased maintenanceAssetLeased);
    List<MaintenanceAssetLeased> getAll(QueryFilter queryFilter);
    Long countTotal();
    MaintenanceAssetLeased getById(Long id);
}
