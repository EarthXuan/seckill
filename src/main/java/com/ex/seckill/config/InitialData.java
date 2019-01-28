package com.ex.seckill.config;

import com.ex.seckill.common.impl.GoodsKey;
import com.ex.seckill.service.GoodsService;
import com.ex.seckill.service.RedisService;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitialData implements InitializingBean{
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    //系统初始化
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list=goodsService.selectListGoodsVo();
        if(null!=list){
            for(GoodsVo goodsVo:list){
                redisService.set(GoodsKey.getSeckillGoodsStock,String.valueOf(goodsVo.getId()),goodsVo.getStockCount());
            }
        }
    }
}
