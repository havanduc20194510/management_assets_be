package com.example.manageasset.domain.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class PagingPayload<T> {
    private T data;
    private Long total;
    private Integer page;
    private Integer limit;

    public static PagingPayload empty() {
        return PagingPayload.builder().build();
    }
}
