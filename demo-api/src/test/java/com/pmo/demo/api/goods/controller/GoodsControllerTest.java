package com.pmo.demo.api.goods.controller;

import com.pmo.demo.api.goods.domain.GoodsRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsControllerTest {

    @Autowired
    GoodsRepository goodsRepository;
}
