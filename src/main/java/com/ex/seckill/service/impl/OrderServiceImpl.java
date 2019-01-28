package com.ex.seckill.service.impl;

import com.ex.seckill.common.impl.OrderKey;
import com.ex.seckill.dao.OrderMapper;
import com.ex.seckill.domain.*;
import com.ex.seckill.service.OrderService;
import com.ex.seckill.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public Order getSeckillOrderByUserIdGoodsId(Long userId, long goodsId) {
//        OrderExample orderExample=new OrderExample();
//        OrderExample.Criteria criteria=orderExample.createCriteria();
//        criteria.andUserIdEqualTo(userId);
//        criteria.andGoodsIdEqualTo(goodsId);
//        List<Order> list= orderMapper.selectByExample(orderExample);
//        if(null==list||list.size()<=0){
//            return null;
//        }
//        return list.get(0);
        return redisService.get(OrderKey.getByUidGid,String.valueOf(userId)+"_"+String.valueOf(goodsId),Order.class);
    }

    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public Order getOrderByid(long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }


}
