package com.pmo.demo.api.search.service;

import com.pmo.demo.api.search.domain.*;
import com.pmo.demo.api.search.dto.GoodsSearchRequestDto;
import com.pmo.demo.api.search.parser.KoreanJamoParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
@Slf4j
public class GoodsSearchService extends SearchService {

    public SearchResult search(GoodsSearchRequestDto searchRequestDTO) throws Exception {
        return super.search(Indicies.GOODS.alias(), makeQuery(searchRequestDTO), GoodsDocument.class);
    }

    private SearchSourceBuilder makeQuery(GoodsSearchRequestDto requestDTO) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        if( StringUtils.isNotEmpty(requestDTO.getKeyword()) ) {
            QueryBuilder multiKeywordQuery = QueryBuilders.multiMatchQuery(requestDTO.getKeyword(), Indicies.GOODS.multiMatchFileds()).operator(Operator.AND);
            query.must(multiKeywordQuery);
        }

        //카테고리 코드
        if ( StringUtils.isNotEmpty(requestDTO.getCategoryCode()) ) {
            QueryBuilder categoryQuery = matchQuery("category_code", requestDTO.getCategoryCode()).operator(Operator.AND);
            query.must(categoryQuery);
        }

        //성분
        if (  StringUtils.isNotEmpty(requestDTO.getIngredients()) ) {
            QueryBuilder categoryQuery = matchQuery("ingredients", requestDTO.getIngredients()).operator(Operator.AND);
            query.must(categoryQuery);
        }

        //kcal
        if ( StringUtils.isNotEmpty(requestDTO.getKcalRangeCode()) ) {
            RangeQueryBuilder kcalQuery = KcalRangeCode.valueOf(requestDTO.getKcalRangeCode()).getQuery();
            query.must(kcalQuery);
        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        log.info("query: {}", searchSourceBuilder.toString());

        return searchSourceBuilder;
    }

    public SearchResult searchAutoComplete(GoodsSearchRequestDto requestDTO) throws Exception {
        return super.search(Indicies.GOODS_AC.alias(), makeQueryForRecommend(requestDTO), GoodsRecommendDocument.class);
    }

    private SearchSourceBuilder makeQueryForRecommend(GoodsSearchRequestDto requestDTO) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        KoreanJamoParser parser = new KoreanJamoParser();
        QueryBuilder termQueryJamo = QueryBuilders.matchQuery("name_jamo", parser.parse(requestDTO.getKeyword())).operator(Operator.AND);
        query.must(termQueryJamo);

//        if("any".equals(requestDTO.getMatchingType())){
//            query.should(termQueryEdgeBack);
//
//        }else if("prefix".equals(requestDTO.getMatchingType())){
//            query.minimumShouldMatch(2);
//        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        log.info(searchSourceBuilder.toString());
        return searchSourceBuilder;
    }
}
