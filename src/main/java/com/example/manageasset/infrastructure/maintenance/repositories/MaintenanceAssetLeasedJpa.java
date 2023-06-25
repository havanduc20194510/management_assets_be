package com.example.manageasset.infrastructure.maintenance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public interface MaintenanceAssetLeasedJpa extends JpaRepository<MaintenanceAssetLeasedEntity, String> {
    MaintenanceAssetLeasedEntity findByIdAndIsDeletedFalse(String id);
    @Modifying
    @Transactional
    @Query("update MaintenanceAssetLeasedEntity m set m.updatedAt = :updatedAt, m.isDeleted = true where m.id = :id")
    void deleteById(@Param("id") String id, @Param("updatedAt") Timestamp updatedAt);
}
