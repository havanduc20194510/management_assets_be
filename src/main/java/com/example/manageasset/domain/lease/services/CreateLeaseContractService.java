package com.example.manageasset.domain.lease.services;

import com.example.manageasset.domain.asset.models.Asset;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.lease.dtos.AssetLeasedDto;
import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.models.AssetLeased;
import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.lease.repositories.LeaseContractRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.shared.utility.ULID;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void create(LeaseContractDto leaseContractDto) throws NotFoundException {
        User user = userRepository.findById(leaseContractDto.getUserDto().getId());
        if(user == null) throw new NotFoundException(String.format("User[id=%d] not found", leaseContractDto.getUserDto().getId()));
        User client = userRepository.findById(leaseContractDto.getClientDto().getId());
        if(client == null) throw new NotFoundException(String.format("Client[id=%d] not found", leaseContractDto.getClientDto().getId()));

        LeaseContract  leaseContract = LeaseContract.create(new ULID().nextULID(), client, user, leaseContractDto.getReason(), new Millisecond(leaseContractDto.getRevokedAt()), new Millisecond(leaseContractDto.getLeasedAt()), leaseContractDto.getNote());

        List<AssetLeased> assetLeaseds = new ArrayList<>();
        for (AssetLeasedDto assetLeasedDto : leaseContractDto.getAssetLeasedDtos()){
            Asset asset = assetRepository.getById(assetLeasedDto.getAssetDto().getId());
            if(asset == null) throw new NotFoundException(String.format("Asset[id=%d] not found", assetLeasedDto.getAssetDto().getId()));
            assetLeaseds.add(AssetLeased.create(assetLeasedDto.getQuantityLease(), asset));
        }
        leaseContract.setAssetLeaseds(assetLeaseds);

        leaseContractRepository.save(leaseContract);

        //TODO update quantity asset
    }
}
