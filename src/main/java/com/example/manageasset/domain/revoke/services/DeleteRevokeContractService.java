package com.example.manageasset.domain.revoke.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteRevokeContractService {
    private final RevokeContractRepository revokeContractRepository;
    private final LeaseContractRepository leaseContractRepository;
    private final AssetRepository assetRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String id) throws NotFoundException {
        RevokeContract revokeContract = revokeContractRepository.getById(id);
        if (revokeContract == null)
            throw new NotFoundException(String.format("RevokeContract[id=%s] not found", id));
        LeaseContract leaseContract = leaseContractRepository.findById(revokeContract.getLeaseContract().getId());
        if (leaseContract == null)
            throw new NotFoundException(String.format("LeaseContract[id=%s] not found", revokeContract.getLeaseContract().getId()));
        List<AssetLeased> assetLeaseds = leaseContract.getAssetLeaseds();
        for (AssetLeased assetLeased : assetLeaseds) {
            Asset asset = assetRepository.getById(assetLeased.getAsset().getId());
            if (asset == null)
                throw new NotFoundException(String.format("Asset[id=%d] not found", assetLeased.getAsset().getId()));

            int quantity = asset.getQuantity();

            if (quantity < assetLeased.getQuantityLease()) {
                throw new InvalidDataException(String.format("Asset[id=%d] have inventory quantity less leased quantity", assetLeased.getAsset().getId()));
            }
            assetRepository.updateQuantity(quantity - assetLeased.getQuantityLease(), asset.getId());
        }
        revokeContract.setIsDeleted(true);
        revokeContractRepository.save(revokeContract);
    }
}
