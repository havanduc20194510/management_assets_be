package com.example.manageasset.infrastructure.maintenance.repositories;

import com.example.manageasset.domain.maintenance.models.MaintenanceAsset;
import com.example.manageasset.infrastructure.lease.repositories.AssetLeasedEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "maintenance_assets")
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceAssetEntity {
    @EmbeddedId
    private MaintenanceAssetLeasedKey id;

    @ManyToOne
    @MapsId("maintenanceId")
    @JoinColumn(name = "maintenance_id")
    private MaintenanceAssetLeasedEntity maintenanceAssetLeased;

    @ManyToOne
    @MapsId("assetLeasedId")
    @JoinColumn(name = "asset_leased_id")
    private AssetLeasedEntity assetLeased;

    public static MaintenanceAssetEntity fromModel(MaintenanceAsset maintenanceAsset){
        return new MaintenanceAssetEntity(
                new MaintenanceAssetLeasedKey(),
                MaintenanceAssetLeasedEntity.fromModel(maintenanceAsset.getMaintenanceAssetLeased()),
                AssetLeasedEntity.fromModel(maintenanceAsset.getAssetLeased(), null)
        );
    }
}
