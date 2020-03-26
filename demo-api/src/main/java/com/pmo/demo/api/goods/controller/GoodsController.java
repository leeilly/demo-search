package com.pmo.demo.api.goods.controller;

import com.pmo.demo.api.goods.domain.GoodsRepository;
import com.pmo.demo.api.goods.dto.GoodsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/goods")
public class GoodsController {
    private GoodsRepository goodsRepository;

    @PostMapping("/add")
    public void saveGoods(@RequestBody GoodsSaveRequestDto dto){
        goodsRepository.save(dto.toEntity());
    }
}
