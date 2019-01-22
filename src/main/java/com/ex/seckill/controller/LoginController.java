package com.ex.seckill.controller;

import com.ex.seckill.domain.User;
import com.ex.seckill.service.UserService;
import com.ex.seckill.utils.ServerResponse;
import com.ex.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/to_login")
    public String toLogin(HttpServletRequest request){
        return "login";
    }
    @RequestMapping("/do_login")
    @ResponseBody
    public ServerResponse doLogin(@Valid LoginVo loginVo, HttpServletResponse response){
         User user=userService.login(response,loginVo);
        return ServerResponse.createBySuccess();
    }
}
