package com.pmo.demo.api.search.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsDocument {
    private Long goodsNo;
    private String goodsName;
    private String brandName;
    private String brandCode;
    private String categoryName;
    private String categoryCode;
    private String ingredients;
    private Long price;
    private Integer kcal;

}
