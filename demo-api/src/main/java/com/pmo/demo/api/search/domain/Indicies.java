package com.pmo.demo.api.search.domain;

public enum Indicies {

    GOODS("goods", new String[]{"goods_name.nori_discard", "category_name", "brand_name"}),
    GOODS_AC("goods_ac", new String[]{} );

    private final String alias;
    private String[] multiMatchFileds;
    public String alias() {
        return alias;
    }
    public String[] multiMatchFileds(){
        return multiMatchFileds;
    }

    Indicies(String alias, String[] multiMatchFileds) {
        this.alias = alias;
        this.multiMatchFileds = multiMatchFileds;
    }
}
