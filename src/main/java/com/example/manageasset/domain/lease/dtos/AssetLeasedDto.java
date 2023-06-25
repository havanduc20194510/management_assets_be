package com.example.manageasset.domain.lease.dtos;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
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
    private Long id;
    @JsonProperty("quantity_lease")
    private Integer quantityLease;
    @JsonProperty("asset")
    private AssetDto assetDto;

    public static AssetLeasedDto fromModel(AssetLeased assetLeased){
        return new AssetLeasedDto(assetLeased.getId(), assetLeased.getQuantityLease(), AssetDto.fromModel(assetLeased.getAsset()));
    }


}
