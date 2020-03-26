package com.pmo.demo.api.goods.dto;

import com.pmo.demo.api.goods.domain.Goods;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoodsSaveRequestDto {
    private String goodsName;
    private String brandName;
    private String brandCode;
    private String categoryCode;
    private String categoryName;
    private String ingredients;
    private Integer kcal;
    private Long price;

    public Goods toEntity(){
        return Goods.builder()
                    .goodsName(goodsName)
                    .brandCode(brandCode)
                    .brandName(brandName)
                    .categoryCode(categoryCode)
                    .categoryName(categoryName)
                    .price(price)
                    .kcal(kcal)
                    .ingredients(ingredients)
                    .build();
    }
}
