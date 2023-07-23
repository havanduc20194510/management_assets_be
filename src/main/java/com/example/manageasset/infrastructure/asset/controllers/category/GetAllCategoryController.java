package com.example.manageasset.infrastructure.asset.controllers.category;

import com.example.manageasset.domain.asset.dtos.CategoryDto;
import com.example.manageasset.domain.asset.services.category.GetAllCategoryService;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class GetAllCategoryController {
    private final GetAllCategoryService getAllCategoryService;

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                  @RequestParam(value = "key", required = false) String searchText) {
        PagingPayload<List<CategoryDto>> result = getAllCategoryService.getAll(limit, page, sort, searchText);
        return new ResponseEntity<>(new ResponseBody(result, ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
