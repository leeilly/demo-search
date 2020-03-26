package com.pmo.demo.api.search.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsIndexRequestDto {
    private String alias;
    private String type;

    @Builder
    public GoodsIndexRequestDto(String alias, String type){
        this.alias = alias;
        this.type = type;
    }
}
