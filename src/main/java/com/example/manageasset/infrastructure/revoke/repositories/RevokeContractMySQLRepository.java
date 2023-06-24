package com.example.manageasset.infrastructure.revoke.repositories;

import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RevokeContractMySQLRepository implements RevokeContractRepository {
    private final RevokeContractJpa revokeContractJpa;
    @Override
    public void save(RevokeContract revokeContract) {
        RevokeContractEntity revokeContractEntity = RevokeContractEntity.fromModel(revokeContract);
        revokeContractJpa.save(revokeContractEntity);
    }

    @Override
    public List<RevokeContract> getAll(QueryFilter queryFilter) {
        return null;
    }

    @Override
    public Long countTotal() {
        return null;
    }

    @Override
    public RevokeContract getById(String id) {
        RevokeContractEntity revokeContract = revokeContractJpa.findByIdAndIsDeletedFalse(id);
        return revokeContract == null ? null : revokeContract.toModel();
    }

    @Override
    public Boolean existedRevokeForLease(String leaseId) {
        return revokeContractJpa.existedRevokeForLease(leaseId);
    }
}
