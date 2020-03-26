package com.pmo.demo.api.search.domain;

import lombok.Getter;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

@Getter
public enum KcalRangeCode {
      KCAL200("200Kcal", QueryBuilders.rangeQuery("kcal").gte(0).lte(200))
    , KCAL300("300kcal", QueryBuilders.rangeQuery("kcal").gte(0).lte(300))
    , KCAL400("400kcal", QueryBuilders.rangeQuery("kcal").gte(0).lte(400))
    , KCAL500("500kcal", QueryBuilders.rangeQuery("kcal").gte(0).lte(500))
    , KCAL1000("1000kcal", QueryBuilders.rangeQuery("kcal").gte(0).lte(1000))
    ;

    private String name;
    private RangeQueryBuilder query;

    KcalRangeCode(String name, RangeQueryBuilder query) {
        this.name = name;
        this.query = query;
    }

}
