package com.example.manageasset.domain.user.repositories;

import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.user.models.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(Long id);
    User findByUsername(String username);
    List<User> list(String key, QueryFilter queryFilter, Long departmentId);
    long totalList(String key, Long departmentId);

}
