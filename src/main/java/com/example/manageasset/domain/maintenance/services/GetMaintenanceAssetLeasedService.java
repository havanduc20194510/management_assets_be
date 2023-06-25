package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final AssetLeasedRepository assetLeasedRepository;

    public MaintenanceAssetLeasedDto get(String id) throws NotFoundException {
        MaintenanceAssetLeased maintenanceAssetLeased = maintenanceAssetLeasedRepository.getById(id);
        if(maintenanceAssetLeased == null) {
            throw new NotFoundException(String.format("MaintenanceAssetLeased[id=%s] not found", id));
        }
        List<AssetLeased> assetLeaseds = assetLeasedRepository.findByMaintenanceId(id);
        maintenanceAssetLeased.setAssetLeaseds(assetLeaseds);
        return MaintenanceAssetLeasedDto.fromModel(maintenanceAssetLeased);
    }
}
