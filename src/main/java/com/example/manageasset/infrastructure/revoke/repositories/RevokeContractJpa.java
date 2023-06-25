package com.example.manageasset.infrastructure.revoke.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface RevokeContractJpa extends JpaRepository<RevokeContractEntity, String> {
    @Query("SELECT r FROM RevokeContractEntity r WHERE r.isDeleted = false")
    List<RevokeContractEntity> findAll();
    @Query("SELECT COUNT(r) FROM RevokeContractEntity r WHERE r.isDeleted = false")
    Long countTotal();
    RevokeContractEntity findByIdAndIsDeletedFalse(String id);
    @Query("SELECT COUNT(r)>0 FROM RevokeContractEntity r WHERE r.isDeleted = false AND r.leaseContract.id = :leaseId")
    Boolean existedRevokeForLease(@Param("leaseId") String leaseId);
    @Query("SELECT r FROM RevokeContractEntity r WHERE r.isDeleted = false " +
            "AND (:searchText is null or r.client.fullName like %:searchText%) " +
            "AND ((:from is null or (r.revokedAt >= :from)) and (:to is null or (r.revokedAt <= :to)))")
    List<RevokeContractEntity> findAll(Pageable pageable, @Param("from") Timestamp from, @Param("to") Timestamp to, @Param("searchText") String searchText);
    @Query("SELECT COUNT(r) FROM RevokeContractEntity r WHERE r.isDeleted = false " +
            "AND (:searchText is null or r.client.fullName like %:searchText%) " +
            "AND ((:from is null or (r.revokedAt >= :from)) and (:to is null or (r.revokedAt <= :to)))")
    Long countTotal(@Param("from") Timestamp from, @Param("to") Timestamp to, @Param("searchText") String searchText);
}
