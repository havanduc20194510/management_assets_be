package com.example.manageasset.domain.user.repositories;

import com.example.manageasset.domain.user.models.User;

public interface UserRepository {
    void save(User user);
}
