package com.ex.seckill.common.impl;

import com.ex.seckill.common.BasePrefix;

public class GoodsKey extends BasePrefix{
    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey getGoodsDetailPage=new GoodsKey(60,"goodsDetailPage");
}
