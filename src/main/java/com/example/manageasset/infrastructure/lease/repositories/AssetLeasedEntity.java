package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.infrastructure.asset.repositories.AssetEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "asset_leaseds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetLeasedEntity {
    @Id
    @Column(name = "asset_code")
    private String assetCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id", nullable = false)
    private AssetEntity asset;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lease_contract_id", nullable = false)
    private LeaseContractEntity leaseContract;

    public static AssetLeasedEntity fromModel(AssetLeased assetLeased, LeaseContractEntity leaseContract){
        return new AssetLeasedEntity(
                assetLeased.getAssetCode(),
                AssetEntity.fromModel(assetLeased.getAsset()),
                leaseContract
        );
    }

    public AssetLeased toModel(){
        return new AssetLeased(
                assetCode,
                asset.toModel()
        );
    }
}
