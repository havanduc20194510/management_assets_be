package com.example.manageasset.domain.user.services.user;

import com.example.manageasset.domain.shared.models.PagingPayload;
import com.example.manageasset.domain.shared.models.QueryFilter;
import com.example.manageasset.domain.user.dtos.DepartmentDto;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.models.Department;
import com.example.manageasset.domain.user.models.User;
import com.example.manageasset.domain.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListUserService {
    private final UserRepository userRepository;

    public ListUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PagingPayload<List<UserDto>> list(String key, Integer page, Integer limit, String sort, Long departmentId){
        QueryFilter filter = QueryFilter.create(limit, page, sort);
        List<User> users = userRepository.list(key, filter, departmentId);
        List<UserDto> userDtos = users.stream().map(UserDto::fromModel).collect(Collectors.toList());
        PagingPayload.PagingPayloadBuilder<List<UserDto>> payloadBuilder = PagingPayload.builder();
        payloadBuilder.data(userDtos);
        payloadBuilder.page(page);
        payloadBuilder.total(userRepository.totalList(key, departmentId));
        payloadBuilder.limit(limit);
        return payloadBuilder.build();
    }
}
