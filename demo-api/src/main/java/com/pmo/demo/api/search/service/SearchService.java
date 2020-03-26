package com.pmo.demo.api.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.pmo.demo.api.search.domain.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    private static final ObjectMapper MAPPER = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @SuppressWarnings("unchecked")
    public SearchResult search(String index, SearchSourceBuilder query, Class valueType) throws Exception {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        searchRequest.source(query);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return SearchResult.builder()
                           .count(searchResponse.getHits().getTotalHits().value)
                           .document(convertResultList(searchResponse, valueType))
                           .build();
    }


    private static List convertResultList(SearchResponse response, Class valueType) {
        int initialSize = (int) (response.getHits().getTotalHits().value * 1.3);
        List list = new ArrayList<>(initialSize);
        response.getHits().forEach(
                hit -> {
                    try {
                        list.add(MAPPER.readValue(hit.getSourceAsString(), valueType));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
        );
        return list;
    }


    @PreDestroy
    public void closeClient() throws Exception {
        restHighLevelClient.close();
    }


}
