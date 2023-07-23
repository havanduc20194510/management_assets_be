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
            "AND (:searchText is null or a.name like %:searchText% or a.status like %:searchText%) " +
            "AND (:categoryId is null or (a.category.id = :categoryId and a.category.isDeleted = false)) " +
            "AND (:state is null or a.state = :state)")
    List<AssetEntity> findAll(Pageable pageable, @Param("searchText") String searchText, @Param("categoryId") Long categoryId, @Param("state") Integer state);
    @Query("SELECT COUNT(a) FROM AssetEntity a WHERE a.isDeleted = false " +
            "AND (:searchText is null or a.name like %:searchText% or a.status like %:searchText%) " +
            "AND (:categoryId is null or (a.category.id = :categoryId and a.category.isDeleted = false)) " +
            "AND (:state is null or a.state = :state)")
    Long countTotal(@Param("searchText") String searchText, @Param("categoryId") Long categoryId, @Param("state") Integer state);
    AssetEntity findByIdAndIsDeletedFalse(Long id);
    List<AssetEntity> findByIdInAndIsDeletedFalse(List<Long> ids);

    @Query("SELECT COUNT(a)>0 FROM AssetEntity a WHERE a.category.id = :categoryId")
    Boolean existedAssetInCategory(@Param("categoryId") Long categoryId);

}
