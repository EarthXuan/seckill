package com.ex.seckill.service;

import com.ex.seckill.vo.GoodsVo;

public interface SecGoodsService {
    boolean reduceStock(GoodsVo goods);
}
