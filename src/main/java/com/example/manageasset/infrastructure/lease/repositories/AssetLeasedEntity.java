package com.example.manageasset.infrastructure.lease.repositories;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.infrastructure.asset.repositories.AssetEntity;
import com.example.manageasset.infrastructure.user.repositories.UserEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "quantity_lease", nullable = false)
    private Integer quantityLease;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id", nullable = false)
    private AssetEntity asset;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lease_contract_id", nullable = false)
    private LeaseContractEntity leaseContract;

    public static AssetLeasedEntity fromModel(AssetLeased assetLeased, LeaseContractEntity leaseContract){
        return new AssetLeasedEntity(
                assetLeased.getId(),
                assetLeased.getQuantityLease(),
                AssetEntity.fromModel(assetLeased.getAsset()),
                leaseContract
        );
    }

    public AssetLeased toModel(){
        return new AssetLeased(
                id,
                quantityLease,
                asset.toModel()
        );
    }
}
