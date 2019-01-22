package com.ex.seckill.service;

import com.ex.seckill.domain.OrderInfo;
import com.ex.seckill.domain.User;
import com.ex.seckill.vo.GoodsVo;

public interface OrderInfoService {
    OrderInfo createOrder(User user, GoodsVo goods);
}
