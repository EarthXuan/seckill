package com.ex.seckill.service;

import com.ex.seckill.domain.OrderInfo;
import com.ex.seckill.domain.User;
import com.ex.seckill.vo.GoodsVo;

import java.awt.image.BufferedImage;

public interface SeckillService {
    OrderInfo seckill(User user, GoodsVo goods);

    long getSeckillResult(Long userId, long goodsId);

    boolean checkPath(User user, long goodsId, String path);

    String createSeckillPath(User user, long goodsId);

    BufferedImage createVerifyCode(User user, long goodsId);

    boolean checkVerifyCode(User user, long goodsId, int verifyCode);
}
