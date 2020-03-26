package com.pmo.demo.api.goods.domain;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Autowired
    GoodsRepository goodsRepository;

//    @After
//    public void cleanup() {
//        goodsRepository.deleteAll();
//    }

    @Test
    public void 상품정보저장_불러오기(){
        //given
        goodsRepository.save(
                Goods.builder()
                     .goodsName("ORGA 입에서 사르르 아이스 홍시 (75gx4)")
                     .brandCode("ORGA")
                     .categoryName("농산물/과일/견과")
                     .ingredients("감자, 우유,  대두, 밀, 난류, 스위트콘")
                     .kcal(100)
                     .price(1000L)
                     .build()
        );

        //when
        List<Goods> goodsList = goodsRepository.findAll();

        //then
        Goods goods = goodsList.get(0);
        assertThat(goods.getGoodsName(), is("ORGA 입에서 사르르 아이스 홍시 (75gx4)"));

    }

}
