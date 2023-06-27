package com.example.manageasset.infrastructure.lease.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssetLeasedJpa extends JpaRepository<AssetLeasedEntity, Long> {
    List<AssetLeasedEntity> findByLeaseContract_Id(String id);

    @Modifying
    @Transactional
    @Query("delete from AssetLeasedEntity a where a.leaseContract.id = :id")
    void deleteAllByLeaseContractId(@Param("id") String id);
    @Query("SELECT a FROM AssetLeasedEntity a, MaintenanceAssetEntity m WHERE m.maintenanceAssetLeased.id = :maintenanceId AND a.id = m.assetLeased.id")
    List<AssetLeasedEntity> findByMaintenanceId(@Param("maintenanceId") String maintenanceId);
    @Query("SELECT COUNT(a)>0 FROM AssetLeasedEntity a WHERE a.asset.id = :assetId")
    Boolean existedAssetForLeased(@Param("assetId") Long assetId);
    @Query("SELECT COUNT(a)>0 FROM AssetLeasedEntity a WHERE a.leaseContract.status = 2 AND a.id in :assetLeasedIds")
    Boolean checkLeaseContractEligibilityToMaintenance(@Param("assetLeasedIds") List<Long> assetLeasedIds);
    @Query("SELECT COUNT(a)>0 FROM AssetLeasedEntity a, RevokeContractEntity r WHERE a.id in :assetLeasedIds " +
            "AND a.leaseContract.id = r.leaseContract.id " +
            "AND r.isDeleted = false")
    Boolean checkLeaseContractExistedRevoke(@Param("assetLeasedIds") List<Long> assetLeasedIds);
}
