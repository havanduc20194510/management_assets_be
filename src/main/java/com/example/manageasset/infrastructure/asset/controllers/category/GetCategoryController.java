package com.example.manageasset.infrastructure.asset.controllers.category;

import com.example.manageasset.domain.asset.dtos.CategoryDto;
import com.example.manageasset.domain.asset.services.category.GetCategoryService;
import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GetCategoryController {
    private final GetCategoryService getCategoryService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) throws NotFoundException {
        CategoryDto categoryDto = getCategoryService.get(id);
        PagingPayload.PagingPayloadBuilder<CategoryDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(categoryDto);
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
