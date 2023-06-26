package com.example.manageasset.infrastructure.revoke.controllers;

import com.example.manageasset.domain.revoke.dtos.RevokeContractDto;
import com.example.manageasset.domain.revoke.services.ListRevokeContractService;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revoke-contract")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ListRevokeContractOfCurrentUserController {
    private final ListRevokeContractService listRevokeContractService;

    @GetMapping("/list-by-user")
    public ResponseEntity<?> listByUser(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                  @RequestParam(value = "from", required = false) Long from,
                                  @RequestParam(value = "to", required = false) Long to,
                                  @RequestParam(value = "key", required = false) String searchText) {
        if(to != null){
            to += 86399000;
        }
        PagingPayload<List<RevokeContractDto>> result = listRevokeContractService.getAll(limit, page, sort, from, to, searchText);
        return new ResponseEntity<>(new ResponseBody(result, ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
