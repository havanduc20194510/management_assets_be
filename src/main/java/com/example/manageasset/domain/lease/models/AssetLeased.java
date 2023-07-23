package com.example.manageasset.domain.lease.models;

import com.example.manageasset.domain.asset.models.Asset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetLeased {
    private String assetCode;
    private Asset asset;

    public AssetLeased(Asset asset) {
        this.asset = asset;
    }

    public static AssetLeased create(Asset asset){
        return new AssetLeased(asset);
    }

    public static AssetLeased create(String assetCode, Asset asset){
        return new AssetLeased(assetCode, asset);
    }

}
