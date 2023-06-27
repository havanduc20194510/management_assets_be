package com.example.manageasset.domain.statistic.models;

import com.example.manageasset.domain.asset.models.Asset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetStatistic {
    private Asset asset;
    private Integer totalQuantityLeased;
    private Integer totalQuantityNotProcess;
}
