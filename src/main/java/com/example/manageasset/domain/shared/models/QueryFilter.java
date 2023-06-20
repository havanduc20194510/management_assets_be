package com.example.manageasset.domain.shared.models;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryFilter {
    private Integer page;
    private Integer limit;
    private String sort;

    public QueryFilter(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    public static QueryFilter create(Integer limit, Integer page, String sort) {
        if (page != null && page < 0) {
            throw new InvalidDataException("QueryFilter[page] must >= 0");
        }

        if (limit != null && limit < 0) {
            throw new InvalidDataException("QueryFilter[limit] must >= 0");
        }

        if(!(sort.equals("asc") || sort.equals("desc"))){
            throw new InvalidDataException("QueryFilter[sort] must 'asc' or 'desc'");
        }
        return new QueryFilter(page, limit, sort);
    }

    public static QueryFilter create(Integer limit, Integer page) {
        if (page != null && page < 0) {
            throw new InvalidDataException("QueryFilter[page] must >= 0");
        }

        if (limit != null && limit < 0) {
            throw new InvalidDataException("QueryFilter[limit] must >= 0");
        }
        return new QueryFilter(page, limit);
    }

}
