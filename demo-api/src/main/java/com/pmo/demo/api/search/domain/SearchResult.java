package com.pmo.demo.api.search.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class SearchResult<T> {
    private Long count;
    private List<T> document;

}
