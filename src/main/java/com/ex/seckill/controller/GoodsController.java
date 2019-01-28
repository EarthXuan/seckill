package com.ex.seckill.controller;

import com.ex.seckill.common.impl.GoodsKey;
import com.ex.seckill.domain.User;
import com.ex.seckill.service.GoodsService;
import com.ex.seckill.service.RedisService;
import com.ex.seckill.service.UserService;
import com.ex.seckill.utils.ServerResponse;
import com.ex.seckill.vo.GoodsDetailVo;
import com.ex.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RequestMapping("/goods")
@Controller
public class GoodsController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping("/to_list")
    public String toList(Model model,User user/*, @CookieValue(value = UserService.COOKIE_NAME_TOKEN,required = false)String cookieToken,
                         @RequestParam(value = UserService.COOKIE_NAME_TOKEN,required = false)String paramToken, HttpServletResponse response*/){
       /* if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            return "login";
        }
        String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        User user=userService.getByToken(response,token);*/
       if(null==user){
           return "login";
       }
       //查询商品列表
        List<GoodsVo> goodsList=goodsService.selectListGoodsVo();
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }
    @RequestMapping(value="/to_detail/{goodsId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<GoodsDetailVo> toDetail(HttpServletRequest request, HttpServletResponse response, Model model, GoodsVo goodsVo, @PathVariable("goodsId")long goodsId, User user){
        if(null==user){
            return ServerResponse.createByErrorMessage("请登陆！");
        }
        if(goodsId==0){
            return ServerResponse.createByErrorMessage("请选择商品！");
        }
        GoodsVo goods=goodsService.selectGoodVoById(goodsId);
        long startAt=goods.getStartDate().getTime();
        long endAt=goods.getEndDate().getTime();
        long now=System.currentTimeMillis();

        int seckillStatus=0;
        int remainSeconds=0;
        if(now<startAt){
            //秒杀还没开始
            seckillStatus=0;
            remainSeconds=(int)(startAt-now)/1000;
        }else if(now>endAt){
            //秒杀已经结束
            seckillStatus=2;
            remainSeconds=-1;
        }else{
            //秒杀正在进行中
            seckillStatus=1;
            remainSeconds=1;
        }
        GoodsDetailVo goodsDetailVo=new GoodsDetailVo();
        goodsDetailVo.setGoods(goods);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setSeckillStatus(seckillStatus);
        return ServerResponse.createBySuccess(goodsDetailVo);
    }

    @RequestMapping(value="/to_detail2/{goodsId}",produces = "text/html")
    @ResponseBody
    public String toDetail2(HttpServletRequest request, HttpServletResponse response,Model model, GoodsVo goodsVo, @PathVariable("goodsId")long goodsId, User user){
        if(goodsId==0){
            return "";
        }
        //取缓存，假如有缓存到页面则直接返回，否则就去读库查找
        String html=redisService.get(GoodsKey.getGoodsDetailPage,String.valueOf(goodsId),String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        GoodsVo goods=goodsService.selectGoodVoById(goodsId);

        long startAt=goods.getStartDate().getTime();
        long endAt=goods.getEndDate().getTime();
        long now=System.currentTimeMillis();

        int seckillStatus=0;
        int remainSeconds=0;

        if(now<startAt){
            //秒杀还没开始
            seckillStatus=0;
            remainSeconds=(int)(startAt-now)/1000;
        }else if(now>endAt){
            //秒杀已经结束
            seckillStatus=2;
            remainSeconds=-1;
        }else{
            //秒杀正在进行中
            seckillStatus=1;
            remainSeconds=1;
        }
        model.addAttribute("seckillStatus",seckillStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("user",user);
        model.addAttribute("goods",goods);
        //通过thymeleafViewResolver生成模板然后放进去redis
//        SpringWebContext,在springboot2.0中已经删除，process方法中ctx所在参数所需要的类型为接口IContext，也就是需要有实现了IContext的类就可以了，然后进入IContext接口找所有的实现类
        WebContext webContext=new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html=thymeleafViewResolver.getTemplateEngine().process("goods_detail",webContext);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetailPage, String.valueOf(goodsId), html);
        }
        return html;
    }


}
