package com.example.manageasset.domain.revoke.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.models.Status;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateRevokeContractService {
    private final RevokeContractRepository revokeContractRepository;
    private final UserRepository userRepository;
    private final LeaseContractRepository leaseContractRepository;
    private final AssetRepository assetRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(RevokeContractDto revokeContractDto) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new NotFoundException(String.format("User[id=%d] not found", revokeContractDto.getUserDto().getId()));
        LeaseContract leaseContract = leaseContractRepository.findById(revokeContractDto.getLeaseContractDto().getId());
        if (leaseContract == null)
            throw new NotFoundException(String.format("LeaseContract[id=%s] not found", revokeContractDto.getLeaseContractDto().getId()));
        if(leaseContract.getStatus().asInt() != Status.APPROVED_TYPE){
            throw new InvalidRequestException(String.format("LeaseContract[id=%s] not approved yet", revokeContractDto.getLeaseContractDto().getId()));
        }
        if(revokeContractRepository.existedRevokeForLease(revokeContractDto.getLeaseContractDto().getId()))
            throw new InvalidRequestException(String.format("LeaseContract[id=%s] was revoked", revokeContractDto.getLeaseContractDto().getId()));
        RevokeContract revokeContract = RevokeContract.create(new ULID().nextULID(), user, revokeContractDto.getReason(), new Millisecond(revokeContractDto.getRevokedAt()), revokeContractDto.getNote(), leaseContract);

        List<AssetLeased> assetLeaseds = leaseContract.getAssetLeaseds();
        for(AssetLeased assetLeased: assetLeaseds){
            Asset asset = assetRepository.getById(assetLeased.getAsset().getId());
            if (asset == null)
                throw new NotFoundException(String.format("Asset[id=%d] not found", assetLeased.getAsset().getId()));
            int quantity = asset.getQuantity();
            assetRepository.updateQuantity(quantity + assetLeased.getQuantityLease(), asset.getId());
        }
        revokeContractRepository.save(revokeContract);
    }
}
