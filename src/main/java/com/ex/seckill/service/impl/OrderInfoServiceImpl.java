package com.ex.seckill.service.impl;

import com.ex.seckill.common.impl.OrderKey;
import com.ex.seckill.dao.OrderInfoMapper;
import com.ex.seckill.domain.Order;
import com.ex.seckill.domain.OrderInfo;
import com.ex.seckill.domain.User;
import com.ex.seckill.service.OrderInfoService;
import com.ex.seckill.service.OrderService;
import com.ex.seckill.service.RedisService;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;
    @Transactional
    @Override
    public OrderInfo createOrder(User user, GoodsVo goods) {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setUserId(user.getId());
        orderInfo.setStatus(0);
        //模拟是pc
        orderInfo.setOrderChannel(1);
        long orderInfoId=orderInfoMapper.insert(orderInfo);
        Order order=new Order();
        order.setGoodsId(goods.getId());
        order.setOrderId(orderInfo.getId());
        order.setUserId(user.getId());
        orderService.insert(order);
        redisService.set(OrderKey.getByUidGid,String.valueOf(user.getId())+"_"+String.valueOf(goods.getId()),order);
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderInfoByid(long orderId) {
        return orderInfoMapper.selectByPrimaryKey(orderId);
    }
}
