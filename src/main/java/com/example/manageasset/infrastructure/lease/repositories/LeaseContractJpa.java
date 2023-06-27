package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.infrastructure.user.repositories.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public interface LeaseContractJpa extends JpaRepository<LeaseContractEntity, String>, JpaSpecificationExecutor<LeaseContractEntity> {
    LeaseContractEntity findByIdAndIsDeletedFalse(String id);

    @Modifying
    @Transactional
    @Query("update LeaseContractEntity l set l.updatedAt = :updatedAt, l.isDeleted = true where l.id = :id")
    void deleteById(@Param("id") String id, @Param("updatedAt")Timestamp updatedAt);
}
