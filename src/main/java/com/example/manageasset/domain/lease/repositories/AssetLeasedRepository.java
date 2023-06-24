package com.example.manageasset.domain.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;

public interface AssetLeasedRepository {
    void deleteAllByLeaseContractId(String id);
    AssetLeased findById(Long id);
}
