package com.example.manageasset.infrastructure.asset.repositories;

import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryMySQLRepository implements CategoryRepository {
    private final CategoryJpa categoryJpa;

    @Override
    public List<Category> getAll(QueryFilter queryFilter, String searchText) {
        Pageable pageable = PageRequest.of(queryFilter.getPage(), queryFilter.getLimit(),
                queryFilter.getSort().equals("asc") ? Sort.by("updatedAt").ascending() : Sort.by("updatedAt").descending());
        return categoryJpa.findAll(pageable, searchText).stream().map(CategoryEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Long countTotal(String searchText) {
        return categoryJpa.countTotal(searchText);
    }

    @Override
    public Category getById(Long id) {
        CategoryEntity categoryEntity = categoryJpa.findByIdAndIsDeletedFalse(id);
        return categoryEntity == null ? null : categoryEntity.toModel();
    }

    @Override
    public void save(Category category) {
        CategoryEntity categoryEntity = CategoryEntity.fromModel(category);
        categoryJpa.save(categoryEntity);
    }

}
