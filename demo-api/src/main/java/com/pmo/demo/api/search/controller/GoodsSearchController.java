package com.pmo.demo.api.search.controller;

import com.pmo.demo.api.search.domain.SearchResult;
import com.pmo.demo.api.search.dto.GoodsSearchRequestDto;
import com.pmo.demo.api.search.service.GoodsSearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/search/goods")
public class GoodsSearchController {

    @Autowired
    GoodsSearchService goodsSearchService;

    @ApiOperation(value="검색", notes = "키워드, 필터 검색")
    @GetMapping("")
    public SearchResult search(@ApiParam("검색어") @RequestParam(value = "keyword") String keyword
                               , @ApiParam("카테고리 코드") @RequestParam(value = "categoryCode", required = false) String categoryCode
                               , @ApiParam("성분") @RequestParam(value = "ingredients", required = false) String ingredients
                               , @ApiParam("칼로리 범위 코드") @RequestParam(value = "kcalRangeCode", required = false) String kcalRangeCode
    ) throws Exception {
        GoodsSearchRequestDto searchRequestDTO = GoodsSearchRequestDto.builder()
                .keyword(keyword)
                .categoryCode(categoryCode)
                .ingredients(ingredients)
                .kcalRangeCode(kcalRangeCode)
                .build();

        return goodsSearchService.search(searchRequestDTO);
    }

    @ApiOperation(value="자동완성 검색", notes = "상품명 추천 검색")
    @GetMapping("/ac")
    public SearchResult search(@ApiParam("검색어") @RequestParam(value = "keyword") String keyword) throws Exception {
        GoodsSearchRequestDto searchRequestDTO = GoodsSearchRequestDto.builder()
                .keyword(keyword)
                .build();

        return goodsSearchService.searchAutoComplete(searchRequestDTO);
    }


}
