package com.ex.seckill.controller;

import com.ex.seckill.domain.Order;
import com.ex.seckill.domain.OrderInfo;
import com.ex.seckill.domain.SecGoods;
import com.ex.seckill.domain.User;
import com.ex.seckill.service.GoodsService;
import com.ex.seckill.service.OrderService;
import com.ex.seckill.service.SeckillService;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    @RequestMapping("/do_seckill")
    public String doSeckill(Model model, User user, @RequestParam("goodsId")long goodsId){
        if(null==user){
            return "lohin";
        }
        //判断库存
        GoodsVo goods=goodsService.selectGoodVoById(goodsId);
        if(null==goods){
            model.addAttribute("errmsg","该商品不存在！");
            return "seckill_fail";
        }
        int stock=goods.getStockCount();
        if(stock<=0){
            model.addAttribute("errmsg","库存不足！");
            return "seckill_fail";
        }
        Order order=orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(null!=order){
            model.addAttribute("errmsg","不能重复秒杀！");
            return "seckill_fail";
        }
        //减库存
        //下订单
        //写入秒杀订单
        OrderInfo orderInfo=seckillService.seckill(user,goods);
        if(null==orderInfo){
            model.addAttribute("errmsg","秒杀失败！");
            return "seckill_fail";
        }
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }
}
