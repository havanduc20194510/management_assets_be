package com.example.manageasset.domain.revoke.services;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteRevokeContractService {
    private final RevokeContractRepository revokeContractRepository;
    private final LeaseContractRepository leaseContractRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String id) throws NotFoundException {
        RevokeContract revokeContract = revokeContractRepository.getById(id);
        if (revokeContract == null)
            throw new NotFoundException(String.format("RevokeContract[id=%s] not found", id));
        LeaseContract leaseContract = leaseContractRepository.findById(revokeContract.getLeaseContract().getId());
        if (leaseContract == null)
            throw new NotFoundException(String.format("LeaseContract[id=%s] not found", revokeContract.getLeaseContract().getId()));

        revokeContract.setIsDeleted(true);
        revokeContractRepository.save(revokeContract);
    }
}
