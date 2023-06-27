package com.example.manageasset.infrastructure.statistic.controllers;

import com.example.manageasset.domain.asset.dtos.AssetDto;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.statistic.dtos.AssetStatisticDto;
import com.example.manageasset.domain.statistic.services.ListAssetStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ListAssetStatisticController {
    private final ListAssetStatisticService listAssetStatisticService;

    @GetMapping("/statistic")
    public ResponseEntity<?> statistic(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                  @RequestParam(value = "category_id", required = false) Long categoryId,
                                  @RequestParam(value = "key", required = false) String searchText,
                                  @RequestParam(value = "from", required = false) Long from,
                                  @RequestParam(value = "to", required = false) Long to) {
        if(to != null){
            to += 86399000;
        }
        PagingPayload<List<AssetStatisticDto>> result = listAssetStatisticService.getAll(limit, page, sort, from, to, searchText, categoryId);
        return new ResponseEntity<>(new ResponseBody(result, ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
