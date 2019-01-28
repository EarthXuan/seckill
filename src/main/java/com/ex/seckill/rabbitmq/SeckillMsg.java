package com.ex.seckill.rabbitmq;

import com.ex.seckill.domain.User;
import com.ex.seckill.vo.GoodsVo;

public class SeckillMsg {
    private User user;
    private GoodsVo goodsVo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }
}
