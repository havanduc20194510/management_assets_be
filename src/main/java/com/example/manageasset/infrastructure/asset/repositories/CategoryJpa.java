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
public interface CategoryJpa extends JpaRepository<CategoryEntity, Long> {
    @Query("SELECT c FROM CategoryEntity c WHERE c.isDeleted = false AND (:searchText is null or c.name like %:searchText%)")
    List<CategoryEntity> findAll(Pageable pageable, @Param("searchText") String searchText);
    @Query("SELECT COUNT(c) FROM CategoryEntity c WHERE c.isDeleted = false AND (:searchText is null or c.name like %:searchText%)")
    Long countTotal(@Param("searchText") String searchText);
    CategoryEntity findByIdAndIsDeletedFalse(Long id);
    @Modifying
    @Transactional
    @Query("UPDATE CategoryEntity c SET c.updatedAt=:updatedAt, c.isDeleted = true WHERE c.id=:id")
    void delete(@Param("id") Long id, @Param("updatedAt") Timestamp updatedAt);
}
