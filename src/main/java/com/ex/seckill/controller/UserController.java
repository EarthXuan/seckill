package com.ex.seckill.controller;

import com.ex.seckill.domain.User;
import com.ex.seckill.service.UserService;
import com.ex.seckill.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user1")
public class UserController {

	@Autowired
    UserService userService;

    @RequestMapping("/info")
    @ResponseBody
    public ServerResponse<User> info(Model model, User user) {
        return ServerResponse.createBySuccess(user);
    }
    
}
