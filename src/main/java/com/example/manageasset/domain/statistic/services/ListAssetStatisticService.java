package com.example.manageasset.domain.statistic.services;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.statistic.dtos.AssetStatisticDto;
import com.example.manageasset.domain.statistic.models.AssetStatistic;
import com.example.manageasset.domain.statistic.repositories.AssetStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListAssetStatisticService {
    private final AssetStatisticRepository assetStatisticRepository;

    public PagingPayload<List<AssetStatisticDto>> getAll(Integer limit, Integer page, String sort, Long from, Long to, String searchText, Long categoryId) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<AssetStatistic> assetStatistics = assetStatisticRepository.getAll(filter, searchText, categoryId, from, to);
        List<AssetStatisticDto> assetStatisticDtos = assetStatistics.stream().map(AssetStatisticDto::fromModel).collect(Collectors.toList());
        PagingPayload.PagingPayloadBuilder<List<AssetStatisticDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(assetStatisticDtos);
        payloadBuilder.page(page);
        payloadBuilder.limit(limit);
        payloadBuilder.total(assetStatisticRepository.countTotal(searchText, categoryId, from, to));
        return payloadBuilder.build();
    }
}
