package com.example.manageasset.domain.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;

import java.util.List;

public interface AssetLeasedRepository {
    void deleteAllByLeaseContractId(String id);
    AssetLeased findById(Long id);
    List<AssetLeased> findByMaintenanceId(String maintenanceId);
    Boolean existedAssetForLeased(Long assetId);
    Boolean checkLeaseContractEligibilityToMaintenance(List<Long> assetLeasedIds);
    Boolean checkLeaseContractExistedRevoke(List<Long> assetLeasedIds);
}
