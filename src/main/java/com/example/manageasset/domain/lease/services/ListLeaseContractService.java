package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.user.dtos.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListLeaseContractService {
    private final LeaseContractRepository leaseContractRepository;

    public ListLeaseContractService(LeaseContractRepository leaseContractRepository) {
        this.leaseContractRepository = leaseContractRepository;
    }

    public PagingPayload<List<LeaseContractDto>> list(String searchText, Integer page, Integer limit, String sort, Long leasedAtFrom, Long leasedAtTo, Integer status){
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<LeaseContract> leaseContract = leaseContractRepository.list(filter, searchText, leasedAtFrom, leasedAtTo, status == null ? null : new Status(status), null);
        PagingPayload.PagingPayloadBuilder<List<LeaseContractDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(leaseContract.stream().map(LeaseContractDto::fromModel).collect(Collectors.toList()));
        payloadBuilder.page(page);
        payloadBuilder.total(leaseContractRepository.totalList(searchText, leasedAtFrom, leasedAtTo, status == null ? null : new Status(status), null));
        payloadBuilder.limit(limit);
        return payloadBuilder.build();
    }

    public PagingPayload<List<LeaseContractDto>> listByUser(String searchText, Integer page, Integer limit, String sort, Long leasedAtFrom, Long leasedAtTo, Integer status){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<LeaseContract> leaseContract = leaseContractRepository.list(filter, searchText, leasedAtFrom, leasedAtTo, status == null ? null : new Status(status), username);
        PagingPayload.PagingPayloadBuilder<List<LeaseContractDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(leaseContract.stream().map(LeaseContractDto::fromModel).collect(Collectors.toList()));
        payloadBuilder.page(page);
        payloadBuilder.total(leaseContractRepository.totalList(searchText, leasedAtFrom, leasedAtTo, status == null ? null : new Status(status), username));
        payloadBuilder.limit(limit);
        return payloadBuilder.build();
    }
}
