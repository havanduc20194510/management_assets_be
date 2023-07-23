package com.example.manageasset.domain.user.services.user;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto get(Long id) throws NotFoundException {
        User user = userRepository.findById(id);
        if(user == null) throw new NotFoundException(String.format("User[id=%d] not found", id));

        return UserDto.fromModel(user);
    }

    public UserDto get(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new NotFoundException(String.format("User[username=%s] not found", username));

        return UserDto.fromModel(user);
    }
}
