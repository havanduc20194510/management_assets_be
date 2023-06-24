package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.models.Millisecond;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
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

    @Override
    public LeaseContract findById(String id) {
        LeaseContractEntity leaseContractEntity = leaseContractJpa.findByIdAndIsDeletedFalse(id);
        if(leaseContractEntity == null) return null;

        List<AssetLeasedEntity> assetLeasedEntities = assetLeasedJpa.findByLeaseContract_Id(id);
        LeaseContract leaseContract = leaseContractEntity.toModel();
        leaseContract.setAssetLeaseds(assetLeasedEntities.stream().map(AssetLeasedEntity::toModel).collect(Collectors.toList()));
        return leaseContract;
    }

    @Override
    public void deleteById(String id) {
        leaseContractJpa.deleteById(id, new Timestamp(Millisecond.now().asLong()));
    }
}
