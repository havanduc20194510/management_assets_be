package com.example.manageasset.infrastructure.user.controllers.user;

import com.example.manageasset.domain.shared.exceptions.NotFoundException;
import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.ResponseBody;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.services.user.ListUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class ListUserController {
    private final ListUserService listUserService;

    public ListUserController(ListUserService listUserService) {
        this.listUserService = listUserService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> get(@RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                 @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
                                 @RequestParam(value = "key", required = false) String key,
                                 @RequestParam(value = "department-id", required = false) Long departmentId) {

        return new ResponseEntity<>(new ResponseBody(listUserService.list(key, page, limit, sort, departmentId), ResponseBody.Status.SUCCESS, ResponseBody.Code.SUCCESS), HttpStatus.OK);
    }
}
