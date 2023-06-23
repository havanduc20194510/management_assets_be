package com.example.manageasset.domain.lease.models;

import com.example.manageasset.domain.asset.models.Asset;
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
}
