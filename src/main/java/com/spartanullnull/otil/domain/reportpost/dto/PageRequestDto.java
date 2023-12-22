package com.spartanullnull.otil.domain.reportpost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Getter
public class PageRequestDto {

    private int page;
    private int size;
    private String sortBy;
    private boolean isAsc;

    public PageRequest toPageable() {
        return PageRequest.of(page - 1, size,
            Sort.by(isAsc ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy)));
    }

}
