package com.ex.seckill.service;

import com.ex.seckill.vo.GoodsVo;

import java.util.List;

public interface GoodsService {

    public List<GoodsVo> selectListGoodsVo();

    GoodsVo selectGoodVoById(long goodsId);

}
