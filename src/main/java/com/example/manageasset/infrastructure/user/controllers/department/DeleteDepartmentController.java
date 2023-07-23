package com.example.manageasset.infrastructure.user.controllers.department;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.services.department.DeleteDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/department")
public class DeleteDepartmentController {
    private final DeleteDepartmentService deleteDepartmentService;

    public DeleteDepartmentController(DeleteDepartmentService deleteDepartmentService) {
        this.deleteDepartmentService = deleteDepartmentService;
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws NotFoundException {
        deleteDepartmentService.delete(id);
        return new ResponseEntity<>(new ResponseBody(PagingPayload.empty(), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
