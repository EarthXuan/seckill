package com.ex.seckill.common.impl;

import com.ex.seckill.common.BasePrefix;

public class OrderKey extends BasePrefix{
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getByUidGid=new OrderKey(3600,"order_seckill");
}
