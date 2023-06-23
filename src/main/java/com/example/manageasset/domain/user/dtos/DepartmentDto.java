package com.example.manageasset.domain.user.dtos;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.models.Department;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentDto {
    private Long id;
    private String name;

    public static DepartmentDto fromModel(Department department){
        return new DepartmentDto(department.getId(), department.getName());
    }
}
