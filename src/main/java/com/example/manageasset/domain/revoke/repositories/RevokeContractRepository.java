package com.example.manageasset.domain.revoke.repositories;

import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.shared.models.QueryFilter;

import java.util.List;

public interface RevokeContractRepository {
    void save(RevokeContract revokeContract);
    List<RevokeContract> getAll(QueryFilter queryFilter, Long from, Long to, String searchText, String username);
    Long countTotal(Long from, Long to, String searchText, String username);
    RevokeContract getById(String id);

    Boolean existedRevokeForLease(String leaseId);
}
