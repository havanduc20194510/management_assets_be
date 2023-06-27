package com.example.manageasset.domain.lease.models;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetLeased {
    private Long id;
    private Integer quantityLease;
    private Asset asset;

    public AssetLeased(Integer quantityLease, Asset asset) {
        this.quantityLease = quantityLease;
        this.asset = asset;
    }

    public static AssetLeased create(Integer quantityLease, Asset asset){
        validate(quantityLease);
        return new AssetLeased(quantityLease, asset);
    }

    public static void validate(Integer quantityLease){
        if(quantityLease == null || quantityLease <= 0) throw new InvalidDataException("Required field AssetLeased[quantityLease]");
    }

    public static AssetLeased create(Long id, Integer quantityLease, Asset asset){
        validate(quantityLease);
        return new AssetLeased(id, quantityLease, asset);
    }

}
