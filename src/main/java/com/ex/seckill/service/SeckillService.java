package com.ex.seckill.service;

import com.ex.seckill.domain.OrderInfo;
import com.ex.seckill.domain.User;
import com.ex.seckill.vo.GoodsVo;

public interface SeckillService {
    OrderInfo seckill(User user, GoodsVo goods);
}
