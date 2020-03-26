package com.pmo.demo.api.search.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsSearchRequestDto {

    private String keyword;
    private Integer kcal;
    private String kcalRangeCode;
    private String categoryCode;
    private String ingredients;

    private Integer page = 1;
    private Integer limit = 30;

    @Builder
    public GoodsSearchRequestDto(String keyword, String categoryCode, Integer kcal, String kcalRangeCode, String ingredients){
        this.keyword = keyword;
        this.kcal = kcal;
        this.kcalRangeCode = kcalRangeCode;
        this.categoryCode = categoryCode;
        this.ingredients = ingredients;
    }

}
