package com.example.manageasset.domain.lease.repositories;

import com.example.manageasset.domain.lease.models.LeaseContract;

public interface LeaseContractRepository {
    void save(LeaseContract leaseContract);
    LeaseContract findById(String id);
    void deleteById(String id);
}
