package com.ex.seckill.service.impl;

import com.ex.seckill.common.impl.TokenKey;
import com.ex.seckill.common.impl.UserKey;
import com.ex.seckill.dao.UserMapper;
import com.ex.seckill.domain.User;
import com.ex.seckill.exception.GlobalException;
import com.ex.seckill.service.RedisService;
import com.ex.seckill.service.UserService;
import com.ex.seckill.utils.MD5Util;
import com.ex.seckill.utils.ServerResponse;
import com.ex.seckill.utils.UUIDUtil;
import com.ex.seckill.vo.LoginVo;
import com.ex.seckill.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;

    public User getUserById(long id){
        //先在redis中查询
        User user=redisService.get(UserKey.getById,String.valueOf(id),User.class);
        if(null!=user){
            return user;
        }
        user=userMapper.selectByPrimaryKey(id);
        redisService.set(UserKey.getById,String.valueOf(id),user);
        return user;
    }
    public User login(HttpServletResponse response, LoginVo loginVo){
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();
        User user=getUserById(Long.valueOf(mobile));
        if(user==null){
//            return ServerResponse.createByErrorMessage("手机号码不存在!");
            throw new GlobalException("手机号码不存在！");
        }
        String dbPassword=user.getPassword();
        String slatDb=user.getSalt();
        String calcPassword=MD5Util.fromPassToDbPass(password,slatDb);
        if(!calcPassword.equals(dbPassword)){
//            return ServerResponse.createByErrorMessage("用户密码错误!");
            throw new GlobalException("用户密码错误！");
        }
        //生成token存入cookie
        String token= UUIDUtil.uuid();
        redisService.set(TokenKey.token,token,user);
        addCookie(response,token,user);
        return user;
    }

    @Override
    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        User user=redisService.get(TokenKey.token,token,User.class);
        if(null!=user){
            addCookie(response,token,user);
        }
        return user;
    }

    @Override
    @Transactional
    public ServerResponse updatePassword(UserVo userVo) {
        try {
            //判断用户是否存在
            User user=getUserById(userVo.getId());
            if(user==null){
                return ServerResponse.createByErrorMessage("用户不存在");
            }
            //判断输入的旧密码是否正确
            String oldPassword=MD5Util.fromPassToDbPass(userVo.getOldPassword(),user.getSalt());
            if(!oldPassword.equals(user.getPassword())){
                return ServerResponse.createByErrorMessage("输入的旧密码不正确");
            }
            //先删除redis中缓存（双写一致）
            redisService.delete(UserKey.getById,String.valueOf(userVo.getId()));
            //更新密码
            User updateUser=new User();
            updateUser.setId(userVo.getId());
            updateUser.setPassword(MD5Util.fromPassToDbPass(userVo.getNewPassword(),user.getSalt()));
            userMapper.updateByPrimaryKeySelective(updateUser);
            user.setPassword(updateUser.getPassword());
            //再删除redis中缓存（双写一致）
            redisService.delete(UserKey.getById,String.valueOf(userVo.getId()));
            //把新的user在redis中更新
            redisService.set(UserKey.getById,String.valueOf(userVo.getId()),user);
            //更新token中的user
            redisService.set(TokenKey.token,userVo.getToken(),user);
            return ServerResponse.createBySuccess(user);
        }catch (Exception e){
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("系统错误");
        }
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(TokenKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(TokenKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
