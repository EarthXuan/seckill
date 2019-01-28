package com.ex.seckill.common.impl;

import com.ex.seckill.common.BasePrefix;
import com.ex.seckill.common.KeyPrefix;

public class SeckillKey extends BasePrefix{
    public SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public SeckillKey(String prefix) {
        super(prefix);
    }

    public static KeyPrefix getSeckillVerifyCode=new SeckillKey(60,"getSeckillVerifyCode");

    public static KeyPrefix getMiaoshaPath=new SeckillKey(60,"getMiaoshaPath");

    public static SeckillKey isGoodsOver=new SeckillKey("goodsOver");
}
