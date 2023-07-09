package com.example.manageasset.domain.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;

import java.util.List;

public interface AssetLeasedRepository {
    void deleteAllByLeaseContractId(String id);
    AssetLeased findByAssetCode(String assetCode);
    List<AssetLeased> findByMaintenanceId(String maintenanceId);
    Boolean existedAssetForLeased(Long assetId);
    Boolean checkLeaseContractEligibilityToMaintenance(List<String> assetCodes);
    Boolean checkLeaseContractExistedRevoke(List<String> assetCodes);
}
