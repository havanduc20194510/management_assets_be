package com.example.manageasset.infrastructure.user.controllers.department;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.services.department.ListDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/department")
public class ListDepartmentController {
    private final ListDepartmentService listDepartmentService;

    public ListDepartmentController(ListDepartmentService listDepartmentService) {
        this.listDepartmentService = listDepartmentService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> list(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                  @RequestParam(value = "key", required = false) String key) {
        return new ResponseEntity<>(new ResponseBody(listDepartmentService.list(key, page, limit, sort), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
