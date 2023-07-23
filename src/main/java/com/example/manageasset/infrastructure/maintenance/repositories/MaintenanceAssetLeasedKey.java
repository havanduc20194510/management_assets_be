package com.example.manageasset.infrastructure.maintenance.repositories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
public class MaintenanceAssetLeasedKey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "maintenance_id")
    private String maintenanceId;

    @Column(name = "asset_code")
    private String assetCode;
}
