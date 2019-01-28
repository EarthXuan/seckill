package com.ex.seckill.service.impl;

import com.ex.seckill.dao.SecGoodsMapper;
import com.ex.seckill.domain.SecGoods;
import com.ex.seckill.service.SecGoodsService;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecGoodsServiceImpl implements SecGoodsService {
    @Autowired
    private SecGoodsMapper secGoodsMapper;

    @Override
    public boolean reduceStock(GoodsVo goods) {
        SecGoods secGoods=new SecGoods();
        secGoods.setGoodsId(goods.getId());
        return secGoodsMapper.reduceSecGoodsStock(secGoods)>0;
    }
}
