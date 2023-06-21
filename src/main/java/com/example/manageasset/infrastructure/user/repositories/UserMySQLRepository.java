package com.example.manageasset.infrastructure.user.repositories;

import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserMySQLRepository implements UserRepository {
    private final UserJpa userJpa;

    public UserMySQLRepository(UserJpa userJpa) {
        this.userJpa = userJpa;
    }

    @Override
    public void save(User user) {
        userJpa.save(UserEntity.fromModel(user));
    }
}
