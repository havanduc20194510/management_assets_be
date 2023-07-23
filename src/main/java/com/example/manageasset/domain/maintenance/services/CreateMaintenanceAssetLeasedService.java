package com.example.manageasset.domain.maintenance.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.State;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.dtos.AssetLeasedDto;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.repositories.AssetLeasedRepository;
import com.example.manageasset.domain.maintenance.dtos.MaintenanceAssetLeasedDto;
import com.example.manageasset.domain.maintenance.models.MaintenanceAssetLeased;
import com.example.manageasset.domain.maintenance.repositories.MaintenanceAssetLeasedRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateMaintenanceAssetLeasedService {
    private final MaintenanceAssetLeasedRepository maintenanceAssetLeasedRepository;
    private final UserRepository userRepository;
    private final AssetLeasedRepository assetLeasedRepository;
    private final AssetRepository assetRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(MaintenanceAssetLeasedDto maintenanceAssetLeasedDto) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User client = userRepository.findByUsername(username);
        if(client == null)
            throw new NotFoundException(String.format("Client[id=%d] not found", maintenanceAssetLeasedDto.getClientDto().getId()));
        if(CollectionUtils.isEmpty(maintenanceAssetLeasedDto.getAssetLeasedDtos())){
            throw new InvalidRequestException("List asset leased cannot empty");
        }
        List<String> assetCodes = new ArrayList<>();
        maintenanceAssetLeasedDto.getAssetLeasedDtos().forEach(assetLeasedDto -> assetCodes.add(assetLeasedDto.getAssetCode()));
        if(!assetLeasedRepository.checkLeaseContractEligibilityToMaintenance(assetCodes)){
            throw new InvalidRequestException("LeaseContract not approved yet");
        }
        if(assetLeasedRepository.checkLeaseContractExistedRevoke(assetCodes))
            throw new InvalidRequestException("LeaseContract have a revoke, cannot create maintenance");
        MaintenanceAssetLeased maintenanceAssetLeased = MaintenanceAssetLeased.create(new ULID().nextULID(), client, maintenanceAssetLeasedDto.getReason(), new Millisecond(maintenanceAssetLeasedDto.getCompletedAt()), new Millisecond(maintenanceAssetLeasedDto.getStartedAt()), maintenanceAssetLeasedDto.getNote());

        List<AssetLeased> assetLeaseds = new ArrayList<>();
        for (AssetLeasedDto assetLeasedDto : maintenanceAssetLeasedDto.getAssetLeasedDtos()) {
            AssetLeased assetLeased = assetLeasedRepository.findByAssetCode(assetLeasedDto.getAssetCode());
            if (assetLeased == null)
                throw new NotFoundException(String.format("AssetLeased[id=%s] not found", assetLeasedDto.getAssetCode()));
            assetLeaseds.add(assetLeased);

            Asset asset = assetLeased.getAsset();
            asset.updateState(State.MAINTENANCE);
            assetRepository.save(asset);
        }

        maintenanceAssetLeased.setAssetLeaseds(assetLeaseds);
        maintenanceAssetLeasedRepository.save(maintenanceAssetLeased);
    }
}
