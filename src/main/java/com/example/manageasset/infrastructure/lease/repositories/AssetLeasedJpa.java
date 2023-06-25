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
}
