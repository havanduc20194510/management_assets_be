package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Repository
public class LeaseContractMySQLRepository implements LeaseContractRepository {
    private final AssetLeasedJpa assetLeasedJpa;
    private final LeaseContractJpa leaseContractJpa;

    public LeaseContractMySQLRepository(AssetLeasedJpa assetLeasedJpa, LeaseContractJpa leaseContractJpa) {
        this.assetLeasedJpa = assetLeasedJpa;
        this.leaseContractJpa = leaseContractJpa;
    }

    @Override
    @Transactional
    public void save(LeaseContract leaseContract) {
        LeaseContractEntity leaseContractEntity = leaseContractJpa.save(LeaseContractEntity.fromModel(leaseContract));
        assetLeasedJpa.saveAll(leaseContract.getAssetLeaseds().stream().map(assetLeased -> AssetLeasedEntity.fromModel(assetLeased, leaseContractEntity)).collect(Collectors.toList()));
    }
}
