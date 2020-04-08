package com.pmo.demo.api.goods.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity(name = "search_boost_map")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryBoost {

    @Id
    @GeneratedValue
    private Long boostSeq;
    private String keyword;
    private String categoryCode;
    private String categoryName;
    private Integer score;
    private String modifiedYmdt;

    @Builder
    public CategoryBoost(String keyword, String categoryCode, String categoryName, Integer score, String modifiedYmdt){
        this.keyword = keyword;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.score = score;
        this.modifiedYmdt = modifiedYmdt;
    }

}
