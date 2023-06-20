package com.example.manageasset.domain.asset.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAssetService {
    private final AssetRepository assetRepository;

    public void delete(Long id) throws NotFoundException {
        Asset asset = assetRepository.getById(id);
        if(asset == null) {
            throw new NotFoundException("Asset not found");
        }
        assetRepository.delete(id);
    }
}
