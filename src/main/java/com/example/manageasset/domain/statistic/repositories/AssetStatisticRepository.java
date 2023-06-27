package com.example.manageasset.domain.statistic.repositories;

import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.statistic.models.AssetStatistic;

import java.util.List;

public interface AssetStatisticRepository {
    List<AssetStatistic> getAll(QueryFilter queryFilter, String searchText, Long categoryId, Long from, Long to);
    Long countTotal(String searchText, Long categoryId, Long from, Long to);
}
