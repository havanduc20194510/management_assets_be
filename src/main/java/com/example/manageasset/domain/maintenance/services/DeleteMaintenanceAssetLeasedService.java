package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.State;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final MaintenanceAssetRepository maintenanceAssetRepository;
    private final AssetRepository assetRepository;
    private final AssetLeasedRepository assetLeasedRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String id) throws NotFoundException {
        MaintenanceAssetLeased maintenanceAssetLeased = maintenanceAssetLeasedRepository.getById(id);
        if (maintenanceAssetLeased == null)
            throw new NotFoundException(String.format("MaintenanceAssetLeased[id=%s] not found", id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if(!maintenanceAssetLeased.getClient().getUsername().equals(username))
            throw new InvalidDataException("Client deleting this maintenance asset leased is not client create");

        List<AssetLeased> assetLeaseds = assetLeasedRepository.findByMaintenanceId(id);
        assetLeaseds.forEach(assetLeased -> {
            Asset asset = assetLeased.getAsset();
            asset.updateState(State.NOT_READY);
            assetRepository.save(asset);
        });

        maintenanceAssetLeasedRepository.delete(id);
        maintenanceAssetRepository.deleteAllByMaintenanceId(id);


    }
}
