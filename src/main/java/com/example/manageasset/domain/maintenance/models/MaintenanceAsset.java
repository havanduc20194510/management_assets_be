package com.example.manageasset.domain.maintenance.models;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceAsset {
    private MaintenanceAssetLeased maintenanceAssetLeased;
    private AssetLeased assetLeased;

    public static MaintenanceAsset create(MaintenanceAssetLeased maintenanceAssetLeased, AssetLeased assetLeased){
        return new MaintenanceAsset(maintenanceAssetLeased, assetLeased);
    }
}
