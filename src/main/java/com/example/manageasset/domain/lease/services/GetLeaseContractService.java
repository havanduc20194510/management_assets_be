package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetLeaseContractService {
    private final LeaseContractRepository leaseContractRepository;

    public GetLeaseContractService(LeaseContractRepository leaseContractRepository) {
        this.leaseContractRepository = leaseContractRepository;
    }

    public LeaseContractDto get(String id) throws NotFoundException {
        LeaseContract leaseContract = leaseContractRepository.findById(id);
        if (leaseContract == null) throw new NotFoundException(String.format("LeaseContract[id=%s] not found", id));
        return LeaseContractDto.fromModel(leaseContract);
    }
}
