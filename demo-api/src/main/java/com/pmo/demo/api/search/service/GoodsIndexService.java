package com.pmo.demo.api.search.service;

import com.pmo.demo.api.goods.domain.CategoryBoostRepository;
import com.pmo.demo.api.goods.domain.GoodsRepository;
import com.pmo.demo.api.search.domain.GoodsRecommendDocument;
import com.pmo.demo.api.search.domain.Indicies;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsIndexService extends IndexService {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    CategoryBoostRepository categoryBoostRepository;

    public RestStatus bulk() throws Exception {
        return super.bulk(Indicies.GOODS.alias(), goodsRepository.findAll()).status();
    }

    public RestStatus bulkAutoComplete() throws Exception {

        List<GoodsRecommendDocument> goodsAcList = new ArrayList<>();
        goodsRepository.findAll().stream().forEach(
            goods -> {
               GoodsRecommendDocument doc = GoodsRecommendDocument.builder()
                                                                  .goodsNo(goods.getGoodsNo())
                                                                  .goodsName(goods.getGoodsName())
                                                                  .build();
               goodsAcList.add(doc);
            }
        );
        return super.bulk(Indicies.GOODS_AC.alias(), goodsAcList).status();
    }

    public RestStatus bulkCategoryBoostMap() throws IOException {
        return super.bulk(Indicies.CATEGORY_BOOST.alias(), categoryBoostRepository.findAll()).status();
    }
}
