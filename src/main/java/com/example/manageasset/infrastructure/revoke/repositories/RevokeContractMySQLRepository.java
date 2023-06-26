package com.example.manageasset.infrastructure.revoke.repositories;

import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<RevokeContract> getAll(QueryFilter queryFilter, Long from, Long to, String searchText, String username) {
        Pageable pageable = PageRequest.of(queryFilter.getPage(), queryFilter.getLimit(),
                queryFilter.getSort().equals("asc") ? Sort.by("revokedAt").ascending() : Sort.by("revokedAt").descending());
        Timestamp fromDate = null;
        if(from != null){
            fromDate = new Timestamp(from);
        }
        Timestamp toDate = null;
        if(to != null){
            toDate = new Timestamp(to);
        }
        return revokeContractJpa.findAll(pageable, fromDate, toDate, searchText, username).stream().map(RevokeContractEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Long countTotal(Long from, Long to, String searchText, String username) {
        Timestamp fromDate = null;
        if(from != null){
            fromDate = new Timestamp(from);
        }
        Timestamp toDate = null;
        if(to != null){
            toDate = new Timestamp(to);
        }
        return revokeContractJpa.countTotal(fromDate, toDate, searchText, username);
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
