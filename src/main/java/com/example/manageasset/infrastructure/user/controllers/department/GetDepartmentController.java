package com.example.manageasset.infrastructure.user.controllers.department;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.services.department.GetDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class GetDepartmentController {
    private final GetDepartmentService getDepartmentService;

    public GetDepartmentController(GetDepartmentService getDepartmentService) {
        this.getDepartmentService = getDepartmentService;
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) throws NotFoundException {
        PagingPayload.PagingPayloadBuilder<DepartmentDto> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(getDepartmentService.get(id));
        return new ResponseEntity<>(new ResponseBody(payloadBuilder.build(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
