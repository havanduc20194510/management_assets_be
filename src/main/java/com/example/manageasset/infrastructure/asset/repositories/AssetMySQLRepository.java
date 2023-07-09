package com.example.manageasset.infrastructure.asset.repositories;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.shared.models.Millisecond;
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
public class AssetMySQLRepository implements AssetRepository {
    private final AssetJpa assetJpa;

    @Override
    public List<Asset> getAll(QueryFilter queryFilter, String searchText, Long categoryId) {
        Pageable pageable = PageRequest.of(queryFilter.getPage(), queryFilter.getLimit(),
                queryFilter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());
        return assetJpa.findAll(pageable, searchText, categoryId).stream().map(AssetEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Long countTotal(String searchText, Long categoryId) {
        return assetJpa.countTotal(searchText, categoryId);
    }

    @Override
    public Asset getById(Long id) {
        AssetEntity assetEntity = assetJpa.findByIdAndIsDeletedFalse(id);
        return assetEntity == null ? null : assetEntity.toModel();
    }

    @Override
    public void save(Asset asset) {
        AssetEntity assetEntity = AssetEntity.fromModel(asset);
        assetJpa.save(assetEntity);
    }

    @Override
    public List<Asset> findByIdIn(List<Long> assetIds) {
        return assetJpa.findByIdInAndIsDeletedFalse(assetIds).stream().map(AssetEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Boolean existedAssetInCategory(Long categoryId) {
        return assetJpa.existedAssetInCategory(categoryId);
    }

}
