package com.example.manageasset.infrastructure.asset.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AssetJpa extends JpaRepository<AssetEntity, Long> {
    @Query("SELECT a FROM AssetEntity a WHERE a.isDeleted = false " +
            "AND (:searchText is null or a.name like %:searchText% or a.managementUnit like %:searchText% or a.status like %:searchText%) " +
            "AND (:categoryId is null or (a.category.id = :categoryId and a.category.isDeleted = false))")
    List<AssetEntity> findAll(Pageable pageable, @Param("searchText") String searchText, @Param("categoryId") Long categoryId);
    @Query("SELECT COUNT(a) FROM AssetEntity a WHERE a.isDeleted = false " +
            "AND (:searchText is null or a.name like %:searchText% or a.managementUnit like %:searchText% or a.status like %:searchText%) " +
            "AND (:categoryId is null or (a.category.id = :categoryId and a.category.isDeleted = false))")
    Long countTotal(@Param("searchText") String searchText, @Param("categoryId") Long categoryId);
    AssetEntity findByIdAndIsDeletedFalse(Long id);
    List<AssetEntity> findByIdInAndIsDeletedFalse(List<Long> ids);

    @Modifying
    @Transactional
    @Query("update AssetEntity asset set asset.quantity=:quantity, asset.updatedAt=:updatedAt where asset.id=:assetId")
    void updateQuantity(@Param("quantity") Integer quantity, @Param("updatedAt") Timestamp updatedAt, @Param("assetId") Long assetId);
}
