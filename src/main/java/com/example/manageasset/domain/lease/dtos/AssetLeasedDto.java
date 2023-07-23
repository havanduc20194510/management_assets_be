package com.example.manageasset.domain.lease.dtos;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.lease.models.AssetLeased;
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
public class AssetLeasedDto {
    @JsonProperty("asset_code")
    private String assetCode;
    @JsonProperty("asset")
    private AssetDto assetDto;

    public static AssetLeasedDto fromModel(AssetLeased assetLeased){
        return new AssetLeasedDto(assetLeased.getAssetCode(), AssetDto.fromModel(assetLeased.getAsset()));
    }

}
