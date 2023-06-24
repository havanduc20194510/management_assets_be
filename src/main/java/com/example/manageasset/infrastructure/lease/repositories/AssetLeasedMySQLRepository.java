package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AssetLeasedMySQLRepository implements AssetLeasedRepository {
    private final AssetLeasedJpa assetLeasedJpa;

    public AssetLeasedMySQLRepository(AssetLeasedJpa assetLeasedJpa) {
        this.assetLeasedJpa = assetLeasedJpa;
    }

    @Override
    public void deleteAllByLeaseContractId(String id) {
        assetLeasedJpa.deleteAllByLeaseContractId(id);
    }
}
