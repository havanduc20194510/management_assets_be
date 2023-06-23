package com.example.manageasset.domain.asset.repositories;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.shared.models.QueryFilter;

import java.util.List;

public interface AssetRepository {
    List<Asset> getAll(QueryFilter queryFilter, String searchText);
    Long countTotal(String searchText);
    Asset getById(Long id);
    void save(Asset asset);
}
