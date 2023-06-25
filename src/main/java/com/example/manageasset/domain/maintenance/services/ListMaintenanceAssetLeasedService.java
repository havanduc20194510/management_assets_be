package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final AssetLeasedRepository assetLeasedRepository;

    public PagingPayload<List<MaintenanceAssetLeasedDto>> getAll(Integer limit, Integer page, String sort, Long from, Long to, String searchText, Integer status) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<MaintenanceAssetLeased> maintenanceAssetLeaseds = maintenanceAssetLeasedRepository.getAll(filter, from, to, searchText, status);
        maintenanceAssetLeaseds.forEach(maintenanceAssetLeased -> {
            List<AssetLeased> assetLeaseds = assetLeasedRepository.findByMaintenanceId(maintenanceAssetLeased.getId());
            maintenanceAssetLeased.setAssetLeaseds(assetLeaseds);
        });
        List<MaintenanceAssetLeasedDto> maintenanceAssetLeasedDtos = maintenanceAssetLeaseds.stream().map(MaintenanceAssetLeasedDto::fromModel).collect(Collectors.toList());
        PagingPayload.PagingPayloadBuilder<List<MaintenanceAssetLeasedDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(maintenanceAssetLeasedDtos);
        payloadBuilder.page(page);
        payloadBuilder.limit(limit);
        payloadBuilder.total(maintenanceAssetLeasedRepository.countTotal(from, to, searchText, status));
        return payloadBuilder.build();
    }
}
