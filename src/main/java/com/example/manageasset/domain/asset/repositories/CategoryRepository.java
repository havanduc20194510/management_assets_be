package com.example.manageasset.domain.asset.repositories;

import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.shared.models.QueryFilter;

import java.util.List;

public interface CategoryRepository {
    List<Category> getAll(QueryFilter queryFilter, String searchText);
    Long countTotal(String searchText);
    Category getById(Long id);
    void save(Category category);
    void delete(Long id);
}
