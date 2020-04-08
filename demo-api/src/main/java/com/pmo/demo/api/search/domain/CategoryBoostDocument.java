package com.pmo.demo.api.search.domain;

import lombok.Getter;

@Getter
public class CategoryBoostDocument {
    private Integer boostSeq;
    private String keyword;
    private String categoryCode;
    private String categoryName;
    private Integer score;
    private String modifiedYmdt;



}
