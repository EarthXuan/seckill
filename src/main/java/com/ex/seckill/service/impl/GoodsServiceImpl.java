package com.ex.seckill.service.impl;

import com.ex.seckill.dao.GoodsMapper;
import com.ex.seckill.domain.Goods;
import com.ex.seckill.domain.SecGoods;
import com.ex.seckill.service.GoodsService;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<GoodsVo> selectListGoodsVo() {
        return goodsMapper.selectListGoodsVo();
    }

    @Override
    public GoodsVo selectGoodVoById(long goodsId) {
        return goodsMapper.selectGoodVoById(goodsId);
    }


}
