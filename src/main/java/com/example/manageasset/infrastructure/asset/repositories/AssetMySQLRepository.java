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
    public List<Asset> getAll(QueryFilter queryFilter, String searchText) {
        Pageable pageable = PageRequest.of(queryFilter.getPage(), queryFilter.getLimit(),
                queryFilter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());
        return assetJpa.findAll(pageable, searchText).stream().map(AssetEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Long countTotal(String searchText) {
        return assetJpa.countTotal(searchText);
    }

    @Override
    public Asset getById(Long id) {
        AssetEntity assetEntity = assetJpa.findByIdAndIsDeletedFalse(id);
        return assetEntity == null ? null : assetEntity.toDomain();
    }

    @Override
    public void save(Asset asset) {
        AssetEntity assetEntity = AssetEntity.fromDomain(asset);
        assetJpa.save(assetEntity);
    }

    @Override
    public void delete(Long id) {
        assetJpa.delete(id,  new Timestamp(Millisecond.now().asLong()));
    }
}
