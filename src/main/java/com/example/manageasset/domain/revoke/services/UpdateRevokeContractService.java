package com.example.manageasset.domain.revoke.services;

import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.revoke.repositories.RevokeContractRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateRevokeContractService {
    private final RevokeContractRepository revokeContractRepository;
    private final UserRepository userRepository;

    public void update(RevokeContractDto revokeContractDto) throws NotFoundException {
        RevokeContract revokeContract = revokeContractRepository.getById(revokeContractDto.getId());
        if (revokeContract == null)
            throw new NotFoundException(String.format("RevokeContract[id=%s] not found", revokeContractDto.getId()));

        User user = userRepository.findById(revokeContractDto.getUserDto().getId());
        if (user == null)
            throw new NotFoundException(String.format("User[id=%d] not found", revokeContractDto.getUserDto().getId()));
        User client = userRepository.findById(revokeContractDto.getClientDto().getId());
        if (client == null)
            throw new NotFoundException(String.format("Client[id=%d] not found", revokeContractDto.getClientDto().getId()));

        revokeContract.update(client, user, revokeContractDto.getReason(), new Millisecond(revokeContractDto.getRevokedAt()), revokeContractDto.getNote());
        revokeContractRepository.save(revokeContract);
    }
}
