package com.example.manageasset.domain.revoke.services;

import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateRevokeContractService {
    private final RevokeContractRepository revokeContractRepository;

    public void update(RevokeContractDto revokeContractDto) throws NotFoundException {
        RevokeContract revokeContract = revokeContractRepository.getById(revokeContractDto.getId());
        if (revokeContract == null)
            throw new NotFoundException(String.format("RevokeContract[id=%s] not found", revokeContractDto.getId()));

        revokeContract.update(revokeContractDto.getReason(), new Millisecond(revokeContractDto.getRevokedAt()), revokeContractDto.getNote());
        revokeContractRepository.save(revokeContract);
    }
}
