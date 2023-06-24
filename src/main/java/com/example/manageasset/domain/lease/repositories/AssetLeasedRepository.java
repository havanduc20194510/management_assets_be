package com.example.manageasset.domain.lease.repositories;

public interface AssetLeasedRepository {
    void deleteAllByLeaseContractId(String id);
}
