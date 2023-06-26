package com.example.manageasset.domain.maintenance.repositories;

import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.shared.models.QueryFilter;

import java.util.List;

public interface MaintenanceAssetLeasedRepository {
    void save(MaintenanceAssetLeased maintenanceAssetLeased);
    List<MaintenanceAssetLeased> getAll(QueryFilter queryFilter, Long from, Long to, String searchText, Integer status, String username);
    Long countTotal(Long from, Long to, String searchText, Integer status, String username);
    MaintenanceAssetLeased getById(String id);
    void delete(String id);
    void changeStatus(String id, Integer status);
}
