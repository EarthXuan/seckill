package com.ex.seckill.controller;

import com.ex.seckill.common.impl.GoodsKey;
import com.ex.seckill.common.impl.OrderKey;
import com.ex.seckill.common.impl.SeckillKey;
import com.ex.seckill.domain.Order;
import com.ex.seckill.domain.User;
import com.ex.seckill.rabbitmq.MQSender;
import com.ex.seckill.rabbitmq.SeckillMsg;
import com.ex.seckill.service.GoodsService;
import com.ex.seckill.service.OrderService;
import com.ex.seckill.service.RedisService;
import com.ex.seckill.service.SeckillService;
import com.ex.seckill.utils.ServerResponse;
import com.ex.seckill.access.AccessLimit;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;


    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.selectListGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getSeckillGoodsStock, ""+goods.getId(), 10);
        }
        redisService.delete(OrderKey.getByUidGid);
        redisService.delete(SeckillKey.isGoodsOver);
        return ServerResponse.createBySuccess(true);
    }

    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(value="/path", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> getMiaoshaPath(HttpServletRequest request, User user,
                                                 @RequestParam("goodsId")long goodsId,
                                                 @RequestParam(value="verifyCode", defaultValue="0")int verifyCode
    ) {
        /*if(user == null) {
            return ServerResponse.createByErrorMessage("请登陆");
        }*/
        boolean check = seckillService.checkVerifyCode(user, goodsId, verifyCode);
        if(!check) {
            return ServerResponse.createByErrorMessage("验证码错误");
        }
        String path  =seckillService.createSeckillPath(user, goodsId);
        return ServerResponse.createBySuccess(path);
    }
    /**
     *  GET和POST区别
     *  GET幂等
     *  POST
     */
    @RequestMapping(value="/{path}/do_seckill",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse doSeckill(Model model, User user, @RequestParam("goodsId")long goodsId,@PathVariable("path") String path){
        if(null==user){
            return ServerResponse.createByErrorMessage("请登陆！");
        }
        //验证path
        boolean check = seckillService.checkPath(user, goodsId, path);
        if(!check){
            return ServerResponse.createByErrorMessage("非法登陆");
        }
        //是否已经秒杀过
        Order order=orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(null!=order){
            return ServerResponse.createByErrorMessage("不能重复秒杀！");
        }
        //redis中库存是否小于0
        Integer redisStock=redisService.get(GoodsKey.getSeckillGoodsStock,String.valueOf(goodsId),Integer.class);
        if(redisStock==null||redisStock<=0){
            return ServerResponse.createByErrorMessage("库存不足！");
        }
        //预减库存
        long stock=redisService.decr(GoodsKey.getSeckillGoodsStock,String.valueOf(goodsId));
        if(stock<0){
            return ServerResponse.createByErrorMessage("库存不足！");
        }
        //写入队列
        SeckillMsg seckillMsg=new SeckillMsg();
        seckillMsg.setUser(user);
        GoodsVo goodsVo=new GoodsVo();
        goodsVo.setId(goodsId);
        seckillMsg.setGoodsVo(goodsVo);
        mqSender.sendSeckillMsg(seckillMsg);

        /*//判断库存
        GoodsVo goods=goodsService.selectGoodVoById(goodsId);
        if(null==goods){
            return ServerResponse.createByErrorMessage("该商品不存在！");
        }
        int stock=goods.getStockCount();
        if(stock<=0){
            return ServerResponse.createByErrorMessage("库存不足！");
        }
        Order order=orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(null!=order){
            return ServerResponse.createByErrorMessage("不能重复秒杀！");
        }
        //减库存
        //下订单
        //写入秒杀订单
        OrderInfo orderInfo=seckillService.seckill(user,goods);
        if(null==orderInfo){
            return ServerResponse.createByErrorMessage("秒杀失败！");
        }*/
        return ServerResponse.createBySuccess("排队中");
    }

    @RequestMapping(value="/result",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse result(Model model, User user, @RequestParam("goodsId")long goodsId){
        if(null==user){
            return ServerResponse.createByErrorMessage("请登陆！");
        }

        long orderId=seckillService.getSeckillResult(user.getId(),goodsId);

        return ServerResponse.createBySuccess(String.valueOf(orderId));
    }

    @RequestMapping(value="/verifyCode", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> getMiaoshaVerifyCod(HttpServletResponse response, User user,
                                                      @RequestParam("goodsId")long goodsId) {
        if(user == null) {
            return ServerResponse.createByErrorMessage("请登陆！");
        }
        try {
            BufferedImage image  = seckillService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("系统错误");
        }
    }
}
