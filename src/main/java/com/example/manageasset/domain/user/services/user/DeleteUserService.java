package com.example.manageasset.domain.user.services.user;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;

    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Long id) throws NotFoundException {
        User user = userRepository.findById(id);
        if(user == null) throw new NotFoundException(String.format("User[id=%d] not found", id));

        user.delete();

        userRepository.save(user);
    }

}
