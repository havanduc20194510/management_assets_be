package com.example.manageasset.infrastructure.revoke.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RevokeContractJpa extends JpaRepository<RevokeContractEntity, String> {
    @Query("SELECT r FROM RevokeContractEntity r WHERE r.isDeleted = false")
    List<RevokeContractEntity> findAll();
    @Query("SELECT COUNT(r) FROM RevokeContractEntity r WHERE r.isDeleted = false")
    Long countTotal();
    RevokeContractEntity findByIdAndIsDeletedFalse(String id);
    @Query("SELECT COUNT(r)>0 FROM RevokeContractEntity r WHERE r.isDeleted = false AND r.leaseContract.id = :leaseId")
    Boolean existedRevokeForLease(@Param("leaseId") String leaseId);
}
