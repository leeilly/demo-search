package com.pmo.demo.api.search.domain;

import com.pmo.demo.api.search.parser.KoreanJamoParser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoodsRecommendDocument {

    private Long goodsNo;
    private String goodsName;
    private String goodsNameJamo;
    private String highlight;

    @Builder
    public GoodsRecommendDocument(Long goodsNo, String goodsName){
        this.goodsNo = goodsNo;
        this.goodsName = goodsName;
        this.goodsNameJamo = new KoreanJamoParser().parse(goodsName);
    }
}
