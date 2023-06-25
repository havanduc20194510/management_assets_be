package com.example.manageasset.infrastructure.lease.controllers;

import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.lease.services.ListLeaseContractService;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/lease-contract")
public class ListLeaseContractController {
    private final ListLeaseContractService listLeaseContractService;

    public ListLeaseContractController(ListLeaseContractService listLeaseContractService) {
        this.listLeaseContractService = listLeaseContractService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "desc") String sort,
                                  @RequestParam(value = "key", required = false) String searchText,
                                  @RequestParam(value = "from", required = false) Long leasedAtFrom,
                                  @RequestParam(value = "to", required = false) Long leasedAtTo) throws ParseException {

        if(leasedAtTo != null) {
            leasedAtTo += 86399000;
        }

        return new ResponseEntity<>(new ResponseBody(listLeaseContractService.list(searchText, page, limit,sort, leasedAtFrom, leasedAtTo), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
