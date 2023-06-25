package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.user.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListLeaseContractService {
    private final LeaseContractRepository leaseContractRepository;

    public ListLeaseContractService(LeaseContractRepository leaseContractRepository) {
        this.leaseContractRepository = leaseContractRepository;
    }

    public PagingPayload<List<LeaseContractDto>> list(String searchText, Integer page, Integer limit, String sort, Long leasedAtFrom, Long leasedAtTo){
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<LeaseContract> leaseContract = leaseContractRepository.list(filter, searchText, leasedAtFrom, leasedAtTo);
        PagingPayload.PagingPayloadBuilder<List<LeaseContractDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(leaseContract.stream().map(LeaseContractDto::fromModel).collect(Collectors.toList()));
        payloadBuilder.page(page);
        payloadBuilder.total(leaseContractRepository.totalList(searchText, leasedAtFrom, leasedAtTo));
        payloadBuilder.limit(limit);
        return payloadBuilder.build();
    }
}
