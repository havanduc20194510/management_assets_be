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
    public AssetLeased findByAssetCode(String assetCode) {
        AssetLeasedEntity assetLeasedEntity = assetLeasedJpa.findByAssetCode(assetCode);
        return assetLeasedEntity == null ? null : assetLeasedEntity.toModel();
    }

    @Override
    public List<AssetLeased> findByMaintenanceId(String maintenanceId) {
        return assetLeasedJpa.findByMaintenanceId(maintenanceId).stream().map(AssetLeasedEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Boolean existedAssetForLeased(Long assetId) {
        return assetLeasedJpa.existedAssetForLeased(assetId);
    }

    @Override
    public Boolean checkLeaseContractEligibilityToMaintenance(List<String> assetCodes) {
        return assetLeasedJpa.checkLeaseContractEligibilityToMaintenance(assetCodes);
    }

    @Override
    public Boolean checkLeaseContractExistedRevoke(List<String> assetCodes) {
        return assetLeasedJpa.checkLeaseContractExistedRevoke(assetCodes);
    }
}
