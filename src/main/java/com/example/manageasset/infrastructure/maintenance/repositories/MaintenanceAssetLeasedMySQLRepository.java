package com.example.manageasset.infrastructure.maintenance.repositories;

import com.example.manageasset.domain.maintenance.models.MaintenanceAsset;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MaintenanceAssetLeasedMySQLRepository implements MaintenanceAssetLeasedRepository {
    private final MaintenanceAssetLeasedJpa maintenanceAssetLeasedJpa;
    private final MaintenanceAssetJpa maintenanceAssetJpa;

    @Override
    @Transactional
    public void save(MaintenanceAssetLeased maintenanceAssetLeased) {
        MaintenanceAssetLeasedEntity maintenanceAssetLeasedEntity = MaintenanceAssetLeasedEntity.fromModel(maintenanceAssetLeased);
        maintenanceAssetLeasedEntity = maintenanceAssetLeasedJpa.save(maintenanceAssetLeasedEntity);
        maintenanceAssetLeased.setId(maintenanceAssetLeasedEntity.getId());

        maintenanceAssetJpa.saveAll(maintenanceAssetLeased.getAssetLeaseds().stream().map(assetLeased -> {
            MaintenanceAsset maintenanceAsset = MaintenanceAsset.create(maintenanceAssetLeased, assetLeased);
            return MaintenanceAssetEntity.fromModel(maintenanceAsset);
        }).collect(Collectors.toList()));
    }

    @Override
    public List<MaintenanceAssetLeased> getAll(QueryFilter queryFilter) {
        return null;
    }

    @Override
    public Long countTotal() {
        return null;
    }

    @Override
    public MaintenanceAssetLeased getById(Long id) {
        return null;
    }
}
