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
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @SuppressWarnings("unchecked")
    private static List convertResultList(SearchResponse response, Class valueType) {
        int initialSize = (int) (response.getHits().getTotalHits().value * 1.3);
        List list = new ArrayList<>(initialSize);

        response.getHits().forEach(
                hit -> {
                    try {
                        if( !hit.getHighlightFields().isEmpty() ) {
                            hit.getSourceAsMap().put("highlight", hit.getHighlightFields().get("goods_name").getFragments()[0].string());
                        }
                        String sourceMapToString = MAPPER.writeValueAsString(hit.getSourceAsMap());
                        list.add(MAPPER.readValue(sourceMapToString, valueType));

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
