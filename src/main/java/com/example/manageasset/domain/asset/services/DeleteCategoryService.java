package com.example.manageasset.domain.asset.services;

import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryService {
    private final CategoryRepository categoryRepository;

    public void delete(Long id) throws NotFoundException {
        Category category = categoryRepository.getById(id);
        if(category == null) {
            throw new NotFoundException("Category not found");
        }
        categoryRepository.delete(id);
    }

}
