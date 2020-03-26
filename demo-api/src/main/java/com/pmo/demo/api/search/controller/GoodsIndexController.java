package com.pmo.demo.api.search.controller;

import com.pmo.demo.api.common.domain.ApiResult;
import com.pmo.demo.api.search.service.GoodsIndexService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/index")
public class GoodsIndexController {

    @Autowired
    GoodsIndexService goodsIndexService;

    @ApiOperation(value="상품 전체 색인", notes = "상품 전체 색인")
    @GetMapping("/bulk/goods")
    public ApiResult index() throws Exception {
        return  ApiResult.ok(goodsIndexService.bulk().getStatus());
    }

    @ApiOperation(value="상품명 자동완성 전체 색인", notes = "상품명 자동완성 전체 색인")
    @GetMapping("/bulk/goods-ac")
    public ApiResult indexAutoComplete() throws Exception {
        return ApiResult.ok(goodsIndexService.bulkAutoComplete().getStatus());
    }

}
