package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.models.State;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.dtos.AssetLeasedDto;
import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateLeaseContractService {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final LeaseContractRepository leaseContractRepository;


    public CreateLeaseContractService(AssetRepository assetRepository, UserRepository userRepository, LeaseContractRepository leaseContractRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.leaseContractRepository = leaseContractRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(LeaseContractDto leaseContractDto) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User client = userRepository.findByUsername(username);
        if (client == null)
            throw new NotFoundException(String.format("Client[id=%d] not found", leaseContractDto.getClientDto().getId()));

        LeaseContract leaseContract = LeaseContract.create(new ULID().nextULID(), client, leaseContractDto.getReason(), new Millisecond(leaseContractDto.getRevokedAt()), new Millisecond(leaseContractDto.getLeasedAt()), leaseContractDto.getNote());

        List<AssetLeased> assetLeaseds = new ArrayList<>();
        for (AssetLeasedDto assetLeasedDto : leaseContractDto.getAssetLeasedDtos()) {
            Asset asset = assetRepository.getById(assetLeasedDto.getAssetDto().getId());
            if (asset == null)
                throw new NotFoundException(String.format("Asset[id=%d] not found", assetLeasedDto.getAssetDto().getId()));

            if(asset.getState().asInt() == State.NOT_READY_TYPE || asset.getState().asInt() == State.MAINTENANCE_TYPE)
                throw new InvalidDataException(String.format("Asset[id=%d] has leased", assetLeasedDto.getAssetDto().getId()));

            asset.updateState(State.NOT_READY);
            assetRepository.save(asset);

            assetLeaseds.add(AssetLeased.create(new ULID().nextULID(), asset));
        }
        leaseContract.setAssetLeaseds(assetLeaseds);

        leaseContractRepository.save(leaseContract);

    }
}
