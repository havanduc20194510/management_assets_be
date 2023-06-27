package com.example.manageasset.domain.statistic.dtos;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.statistic.models.AssetStatistic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetStatisticDto {
    @JsonProperty("asset")
    private AssetDto assetDto;
    @JsonProperty("total_quantity_leased")
    private Integer totalQuantityLeased;
    @JsonProperty("total_quantity_not_process")
    private Integer totalQuantityNotProcess;

    public static AssetStatisticDto fromModel(AssetStatistic assetStatistic) {
        return new AssetStatisticDto(
                AssetDto.fromModel(assetStatistic.getAsset()),
                assetStatistic.getTotalQuantityLeased(),
                assetStatistic.getTotalQuantityNotProcess()
        );
    }
}
