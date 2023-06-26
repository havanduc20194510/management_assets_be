package com.example.manageasset.domain.asset.services.asset;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAssetService {
    private final AssetRepository assetRepository;
    private final AssetLeasedRepository assetLeasedRepository;

    public void delete(Long id) throws NotFoundException {
        Asset asset = assetRepository.getById(id);
        if(asset == null) {
            throw new NotFoundException(String.format("Asset[id=%d] not found", id));
        }
        if(assetLeasedRepository.existedAssetForLeased(id)){
            throw new InvalidRequestException(String.format("Asset[id=%d] once leased, cannot be deleted", id));
        }
        asset.delete();
        assetRepository.save(asset);
    }
}
