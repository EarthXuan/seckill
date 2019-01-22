package com.ex.seckill.service;

import com.ex.seckill.domain.Order;

public interface OrderService {
    Order getSeckillOrderByUserIdGoodsId(Long userId, long goodsId);

    int insert(Order order);
}
