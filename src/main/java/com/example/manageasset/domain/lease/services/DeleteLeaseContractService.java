package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteLeaseContractService {
    private final LeaseContractRepository leaseContractRepository;
    private final AssetLeasedRepository assetLeasedRepository;
    private final CheckProcessLeaseContractService checkProcessLeaseContractService;

    public DeleteLeaseContractService(LeaseContractRepository leaseContractRepository, AssetLeasedRepository assetLeasedRepository, CheckProcessLeaseContractService checkProcessLeaseContractService) {
        this.leaseContractRepository = leaseContractRepository;
        this.assetLeasedRepository = assetLeasedRepository;
        this.checkProcessLeaseContractService = checkProcessLeaseContractService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String id) throws NotFoundException {
        LeaseContract leaseContract = leaseContractRepository.findById(id);
        if (leaseContract == null) throw new NotFoundException(String.format("LeaseContract[id=%s] not found", id));

        checkProcessLeaseContractService.check(leaseContract);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(!leaseContract.getClient().getUsername().equals(username)) throw new  InvalidDataException("Client is deleting lease contract is not client create lease contract");

        assetLeasedRepository.deleteAllByLeaseContractId(leaseContract.getId());
        leaseContractRepository.deleteById(leaseContract.getId());
    }
}
