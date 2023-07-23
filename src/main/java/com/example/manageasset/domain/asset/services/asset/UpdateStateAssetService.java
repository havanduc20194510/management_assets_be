package com.example.manageasset.domain.asset.services.asset;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.State;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateStateAssetService {
    private final AssetRepository assetRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void update(Long id, Integer state) throws NotFoundException {
        Asset asset = assetRepository.getById(id);
        asset.updateState(new State(state));
        assetRepository.save(asset);
    }
}
