package com.spartanullnull.otil.domain.reportpost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageRequestDto {

    private int page;
    private int size;
    private String sortBy;
    private boolean isAsc;

}
