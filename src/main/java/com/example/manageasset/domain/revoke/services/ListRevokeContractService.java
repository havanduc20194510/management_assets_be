package com.example.manageasset.domain.revoke.services;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListRevokeContractService {
    private final RevokeContractRepository revokeContractRepository;
    private final LeaseContractRepository leaseContractRepository;

    public PagingPayload<List<RevokeContractDto>> getAll(Integer limit, Integer page, String sort, Long from, Long to, String searchText) {
        String username = null;
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<RevokeContract> revokeContracts = revokeContractRepository.getAll(filter, from, to, searchText, username);
        revokeContracts.forEach(revokeContract -> {
            LeaseContract leaseContract = leaseContractRepository.findById(revokeContract.getLeaseContract().getId());
            revokeContract.setLeaseContract(leaseContract);
        });
        List<RevokeContractDto> revokeContractDtos = revokeContracts.stream().map(RevokeContractDto::fromModel).collect(Collectors.toList());
        PagingPayload.PagingPayloadBuilder<List<RevokeContractDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(revokeContractDtos);
        payloadBuilder.page(page);
        payloadBuilder.limit(limit);
        payloadBuilder.total(revokeContractRepository.countTotal(from, to, searchText, username));
        return payloadBuilder.build();
    }
}
