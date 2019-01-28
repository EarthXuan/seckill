package com.ex.seckill.controller;

import com.ex.seckill.domain.Goods;
import com.ex.seckill.domain.Order;
import com.ex.seckill.domain.OrderInfo;
import com.ex.seckill.domain.User;
import com.ex.seckill.service.GoodsService;
import com.ex.seckill.service.OrderInfoService;
import com.ex.seckill.service.OrderService;
import com.ex.seckill.utils.ServerResponse;
import com.ex.seckill.vo.GoodsVo;
import com.ex.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse detail(Model model, User user, @RequestParam("orderInfoId") long orderInfoId){
        if(null==user){
            return ServerResponse.createByErrorMessage("请登陆！");
        }
        OrderInfo orderInfo=orderInfoService.getOrderInfoByid(orderInfoId);
        GoodsVo goods=goodsService.selectGoodVoById(orderInfo.getGoodsId());
        OrderDetailVo orderDetailVo=new OrderDetailVo();
        orderDetailVo.setOrderInfo(orderInfo);
        orderDetailVo.setGoods(goods);
        return ServerResponse.createBySuccess(orderDetailVo);
    }
}
