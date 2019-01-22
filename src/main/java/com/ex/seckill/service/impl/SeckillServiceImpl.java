package com.ex.seckill.service.impl;

import com.ex.seckill.domain.OrderInfo;
import com.ex.seckill.domain.User;
import com.ex.seckill.service.SecGoodsService;
import com.ex.seckill.service.SeckillService;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeckillServiceImpl implements SeckillService{

    @Autowired
    private SecGoodsService secGoodsService;
    @Autowired
    private OrderInfoServiceImpl orderInfoService;

    @Transactional
    @Override
    public OrderInfo seckill(User user, GoodsVo goods) {
        //减库存
        secGoodsService.reduceStock(goods);
        //下订单
        //写入秒杀订单(orderInfo,order)
        return orderInfoService.createOrder(user,goods);
    }
}
