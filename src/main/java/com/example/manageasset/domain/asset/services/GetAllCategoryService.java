package com.example.manageasset.domain.asset.services;

import com.example.manageasset.domain.asset.dtos.CategoryDto;
import com.example.manageasset.domain.asset.models.Category;
import com.example.manageasset.domain.asset.repositories.CategoryRepository;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllCategoryService {
    private final CategoryRepository categoryRepository;

    public PagingPayload<List<CategoryDto>> getAll(Integer limit, Integer page, String sort, String searchText) {
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<Category> categories = categoryRepository.getAll(filter, searchText);
        List<CategoryDto> categoryDtos = categories.stream().map(CategoryDto::fromModel).collect(Collectors.toList());
        PagingPayload.PagingPayloadBuilder<List<CategoryDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(categoryDtos);
        payloadBuilder.page(page);
        payloadBuilder.size(limit);
        payloadBuilder.total(categoryRepository.countTotal(searchText));
        return payloadBuilder.build();
    }
}
