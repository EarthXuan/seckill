package com.ex.seckill.service;

import com.ex.seckill.domain.User;
import com.ex.seckill.utils.ServerResponse;
import com.ex.seckill.vo.LoginVo;
import com.ex.seckill.vo.UserVo;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    public static final String COOKIE_NAME_TOKEN="token";
    User getUserById(long id);

    User login(HttpServletResponse response,LoginVo loginVo);

    User getByToken(HttpServletResponse response, String token);

    ServerResponse updatePassword(UserVo userVo);

}
