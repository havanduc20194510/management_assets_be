package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AssetLeasedMySQLRepository implements AssetLeasedRepository {
    private final AssetLeasedJpa assetLeasedJpa;

    public AssetLeasedMySQLRepository(AssetLeasedJpa assetLeasedJpa) {
        this.assetLeasedJpa = assetLeasedJpa;
    }

    @Override
    public void deleteAllByLeaseContractId(String id) {
        assetLeasedJpa.deleteAllByLeaseContractId(id);
    }

    @Override
    public AssetLeased findById(Long id) {
        Optional<AssetLeasedEntity> opt = assetLeasedJpa.findById(id);
        return opt.map(AssetLeasedEntity::toModel).orElse(null);
    }

    @Override
    public List<AssetLeased> findByMaintenanceId(String maintenanceId) {
        return assetLeasedJpa.findByMaintenanceId(maintenanceId).stream().map(AssetLeasedEntity::toModel).collect(Collectors.toList());
    }
}
