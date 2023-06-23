package com.example.manageasset.domain.asset.services.category;

import com.example.manageasset.domain.asset.dtos.CategoryDto;
import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoryService {
    private final CategoryRepository categoryRepository;

    public void update(CategoryDto categoryDto) throws NotFoundException {
        Category category = categoryRepository.getById(categoryDto.getId());
        if(category == null) {
            throw new NotFoundException(String.format("Category[id=%d] not found", categoryDto.getId()));
        }
        category.update(categoryDto.getName());
        categoryRepository.save(category);
    }
}
