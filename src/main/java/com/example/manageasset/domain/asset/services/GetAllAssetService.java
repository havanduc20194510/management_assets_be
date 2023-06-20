package com.example.manageasset.domain.asset.services;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllAssetService {
    private final AssetRepository assetRepository;

    public PagingPayload<List<AssetDto>> getAll(Integer limit, Integer page, String sort, String searchText) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<Asset> assets = assetRepository.getAll(filter, searchText);
        List<AssetDto> assetDtos = assets.stream().map(AssetDto::fromModel).collect(Collectors.toList());
        PagingPayload.PagingPayloadBuilder<List<AssetDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(assetDtos);
        payloadBuilder.page(page);
        payloadBuilder.size(limit);
        payloadBuilder.total(assetRepository.countTotal(searchText));
        return payloadBuilder.build();
    }
}
