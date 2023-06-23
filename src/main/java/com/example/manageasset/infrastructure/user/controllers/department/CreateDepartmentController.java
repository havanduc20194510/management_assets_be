package com.example.manageasset.infrastructure.user.controllers.department;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.services.department.CreateDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/department")
public class CreateDepartmentController {
    private final CreateDepartmentService createDepartmentService;

    public CreateDepartmentController(CreateDepartmentService createDepartmentService) {
        this.createDepartmentService = createDepartmentService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody DepartmentDto departmentDto) {
        createDepartmentService.create(departmentDto);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
