package com.example.manageasset.domain.revoke.services;

import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRevokeContractService {
    private final RevokeContractRepository revokeContractRepository;

    public void delete(String id) throws NotFoundException {
        RevokeContract revokeContract = revokeContractRepository.getById(id);
        if (revokeContract == null)
            throw new NotFoundException(String.format("RevokeContract[id=%s] not found", id));
        revokeContract.setIsDeleted(true);
        revokeContractRepository.save(revokeContract);
    }
}
