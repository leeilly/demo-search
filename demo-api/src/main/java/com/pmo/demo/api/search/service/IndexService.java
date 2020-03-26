package com.pmo.demo.api.search.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
public class IndexService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    private static final ObjectMapper MAPPER = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    /**
     * 전체 색인
     * @param alias
     * @param list
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    BulkResponse bulk(String alias, List<?> list) throws IOException {
        String newIndex = alias + "_" + new SimpleDateFormat( "yyyyMMddHHmm").format(new Date());
        log.info("newIndex: {}", newIndex);

        BulkRequest request = new BulkRequest();
        list.forEach(data -> request.add(createIndexRequest(newIndex, MAPPER.convertValue(data, Map.class))));
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

        if( !response.hasFailures() ) switchAlias(newIndex, alias);

        return response;
    }


    private IndexRequest createIndexRequest(String index, Map<String, Object> data) {
        IndexRequest request = new IndexRequest(index);
        request.source(data);
        return request;
    }


    private boolean isExistAlias(String alias) throws IOException {
        GetAliasesRequest requestWithAlias = new GetAliasesRequest(alias);
        return restHighLevelClient.indices().existsAlias(requestWithAlias, RequestOptions.DEFAULT);
    }

    private boolean switchAlias(String newIndex, String alias) throws IOException {

        if( !isExistAlias(alias) ) return false;
        if( !removeAlias(getCurrentAliasIndexName(alias), alias) ) return false;
        return addAlias(newIndex, alias);

    }

    private boolean addAlias(String index, String alias) throws IOException {

        IndicesAliasesRequest request = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).index(index).alias(alias);
        request.addAliasAction(aliasAction);

        AcknowledgedResponse indicesAliasesResponse = restHighLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);

        return indicesAliasesResponse.isAcknowledged();

    }

    private boolean removeAlias(String index, String alias) throws IOException {

        IndicesAliasesRequest request = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE).index(index).alias(alias);
        request.addAliasAction(aliasAction);

        AcknowledgedResponse indicesAliasesResponse = restHighLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);

        return indicesAliasesResponse.isAcknowledged();
    }


    private String getCurrentAliasIndexName(String alias) throws IOException {
        GetAliasesRequest requestWithAlias = new GetAliasesRequest(alias);
        GetAliasesResponse response = restHighLevelClient.indices().getAlias(requestWithAlias, RequestOptions.DEFAULT);

        String currentIndex = "";
        if( response.getAliases().keySet().size() != 1 ) return currentIndex;
        for( String index : response.getAliases().keySet() ){
            currentIndex = index;
        }
        return currentIndex;
    }

}
