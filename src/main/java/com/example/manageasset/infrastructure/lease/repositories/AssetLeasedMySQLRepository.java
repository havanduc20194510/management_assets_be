package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Override
    public AssetLeased findById(Long id) {
        Optional<AssetLeasedEntity> opt = assetLeasedJpa.findById(id);
        return opt.map(AssetLeasedEntity::toModel).orElse(null);
    }
}
