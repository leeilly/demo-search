package com.pmo.demo.api.goods.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {

    @Id
    @GeneratedValue
    private Long goodsNo;

    private String goodsName;
    private String brandName;
    private String brandCode;
    private String categoryName;
    private String categoryCode;

    private String ingredients;
    private Long price;
    private Integer kcal;

    @Builder
    public Goods(String goodsName, String brandCode, String brandName, String categoryName, String categoryCode, String ingredients, Long price, Integer kcal){
        this.goodsName = goodsName;
        this.brandName = brandName;
        this.brandCode = brandCode;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.ingredients = ingredients;
        this.price = price;
        this.kcal = kcal;
    }


}
