package com.example.manageasset.infrastructure.maintenance.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface MaintenanceAssetLeasedJpa extends JpaRepository<MaintenanceAssetLeasedEntity, String> {
    MaintenanceAssetLeasedEntity findByIdAndIsDeletedFalse(String id);
    @Modifying
    @Transactional
    @Query("update MaintenanceAssetLeasedEntity m set m.updatedAt = :updatedAt, m.isDeleted = true where m.id = :id")
    void deleteById(@Param("id") String id, @Param("updatedAt") Timestamp updatedAt);
    @Query("SELECT m FROM MaintenanceAssetLeasedEntity m WHERE m.isDeleted = false " +
            "AND (:searchText is null or m.client.fullName like %:searchText%) " +
            "AND ((:from is null or (m.startedAt >= :from)) and (:to is null or (m.startedAt <= :to)))")
    List<MaintenanceAssetLeasedEntity> findAll(Pageable pageable, @Param("from") Timestamp from, @Param("to") Timestamp to, @Param("searchText") String searchText);
    @Query("SELECT COUNT(m) FROM MaintenanceAssetLeasedEntity m WHERE m.isDeleted = false " +
            "AND (:searchText is null or m.client.fullName like %:searchText%) " +
            "AND ((:from is null or (m.startedAt >= :from)) and (:to is null or (m.startedAt <= :to)))")
    Long countTotal(@Param("from") Timestamp from, @Param("to") Timestamp to, @Param("searchText") String searchText);
}
