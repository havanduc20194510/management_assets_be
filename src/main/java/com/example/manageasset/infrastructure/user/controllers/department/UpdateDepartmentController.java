package com.example.manageasset.infrastructure.user.controllers.department;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.services.department.UpdateDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/department")
public class UpdateDepartmentController {
    private final UpdateDepartmentService updateDepartmentService;

    public UpdateDepartmentController(UpdateDepartmentService updateDepartmentService) {
        this.updateDepartmentService = updateDepartmentService;
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody DepartmentDto departmentDto) throws NotFoundException {
        updateDepartmentService.update(departmentDto);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
