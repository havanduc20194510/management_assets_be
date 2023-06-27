package com.example.manageasset.domain.asset.services.category;

import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.AssetRepository;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.exceptions.InvalidRequestException;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryService {
    private final CategoryRepository categoryRepository;
    private final AssetRepository assetRepository;

    public void delete(Long id) throws NotFoundException {
        Category category = categoryRepository.getById(id);
        if(category == null) {
            throw new NotFoundException(String.format("Category[id=%d] not found", id));
        }
        if(assetRepository.existedAssetInCategory(id)){
            throw new InvalidRequestException(String.format("Category[id=%d] existing asset, cannot be deleted", id));
        }
        category.delete();
        categoryRepository.save(category);
    }

}
