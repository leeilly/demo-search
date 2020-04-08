package com.pmo.demo.api.search.service;

import com.pmo.demo.api.search.domain.*;
import com.pmo.demo.api.search.dto.GoodsSearchRequestDto;
import com.pmo.demo.api.search.parser.KoreanJamoParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoodsSearchService extends SearchService {

    public SearchResult search(GoodsSearchRequestDto searchRequestDTO) throws Exception {
        return super.search(Indicies.GOODS.alias(), makeQuery(searchRequestDTO), GoodsDocument.class);
    }

    private SearchSourceBuilder makeQuery(GoodsSearchRequestDto requestDTO) throws Exception {
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        if (StringUtils.isNotEmpty(requestDTO.getKeyword())) {
            QueryBuilder multiKeywordQuery = QueryBuilders.multiMatchQuery(requestDTO.getKeyword(), Indicies.GOODS.multiMatchFileds()).operator(Operator.AND);
            query.must(multiKeywordQuery);
        }

        //fixme: refactor category boosting
        query = appendCategoryBoostQuery(query, requestDTO);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);

        //log.info("query: {}", searchSourceBuilder.toString());

        return searchSourceBuilder;
    }

    private BoolQueryBuilder appendCategoryBoostQuery(BoolQueryBuilder query, GoodsSearchRequestDto requestDTO) throws Exception {
        SearchResult boostSearchResult = super.search(Indicies.CATEGORY_BOOST.alias(), makeQueryForCategoryBoost(requestDTO.getKeyword()), CategoryBoostDocument.class );

        Map<String, Integer> map = new HashMap<>();
        List<CategoryBoostDocument> list = boostSearchResult.getDocument();
        list.stream().forEach(b -> map.put(b.getCategoryCode(), b.getScore()));

        for( String categoryCode : map.keySet() ){
            QueryBuilder boostQuery = QueryBuilders.matchQuery("category_code", categoryCode).operator(Operator.AND).boost(map.get(categoryCode));
            query.should(boostQuery);
        }

        return query;
    }

    private SearchSourceBuilder makeQueryForCategoryBoost(String keyword) {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        QueryBuilder keywordQuery = QueryBuilders.matchQuery("keyword", keyword).operator(Operator.AND);
        query.must(keywordQuery);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        return searchSourceBuilder;
    }


    public SearchResult searchAutoComplete(GoodsSearchRequestDto requestDTO) throws Exception {
        return super.search(Indicies.GOODS_AC.alias(), makeQueryForAutoComplete(requestDTO), GoodsRecommendDocument.class);
    }

    private SearchSourceBuilder makeQueryForAutoComplete(GoodsSearchRequestDto requestDTO) {

        KoreanJamoParser parser = new KoreanJamoParser();
        BoolQueryBuilder query = QueryBuilders.boolQuery();

        QueryBuilder termQueryNgram = QueryBuilders.termQuery("goods_name", requestDTO.getKeyword());
        QueryBuilder matchQueryJamo = QueryBuilders.matchPhrasePrefixQuery("goods_name_jamo", parser.parse(requestDTO.getKeyword()));

        query.should(termQueryNgram);
        query.should(matchQueryJamo);
        query.minimumShouldMatch(1);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.highlighter(new HighlightBuilder().field("goods_name"));

        log.info(searchSourceBuilder.toString());
        return searchSourceBuilder;
    }



}
