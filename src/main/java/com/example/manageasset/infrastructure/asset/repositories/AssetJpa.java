package com.example.manageasset.infrastructure.asset.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetJpa extends JpaRepository<AssetEntity, Long> {
    @Query("SELECT a FROM AssetEntity a WHERE a.isDeleted = false AND (:searchText is null or a.name like %:searchText%)")
    List<AssetEntity> findAll(Pageable pageable, @Param("searchText") String searchText);
    @Query("SELECT COUNT(a) FROM AssetEntity a WHERE a.isDeleted = false AND (:searchText is null or a.name like %:searchText%)")
    Long countTotal(@Param("searchText") String searchText);
    AssetEntity findByIdAndIsDeletedFalse(Long id);
}
