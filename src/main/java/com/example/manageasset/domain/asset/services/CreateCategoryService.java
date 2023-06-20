package com.example.manageasset.domain.asset.services;

import com.example.manageasset.domain.asset.dtos.CategoryDto;
import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {
    private final CategoryRepository categoryRepository;

    public void create(CategoryDto categoryDto) {
        Category category = Category.create(categoryDto.getName());
        categoryRepository.save(category);
    }
}
