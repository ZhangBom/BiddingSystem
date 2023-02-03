package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.pojo.LoginUser;
import com.zhangbo.pojo.TabVendor;
import com.zhangbo.pojo.User;
import com.zhangbo.pojo.UserInfo;
import com.zhangbo.service.UserService;
import com.zhangbo.until.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.zhangbo.mapper.UserMapper;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisCache redisCache;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        //判断用户是否存在（同时验证密码）
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            return Result.resultFactory(Status.LOGIN_FAIL);
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = String.valueOf(loginUser.getUser().getUserId());
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        String redisKey = "user:" + userId;
        redisCache.setCacheObject(redisKey, loginUser, 60 * 60* 24, TimeUnit.SECONDS);
        //把token响应给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return Result.resultFactory(Status.LOGIN_SUCCESS, map);
    }

    /**
     * 用户登出
     * @return
     */
    @Override
    public Result logout() {
        //从authentication中获取用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String  userid = loginUser.getUser().getUserId();
        //从redis中删除用户信息
        String redisKey = "user:" + userid;
        redisCache.deleteObject(redisKey);
        return Result.resultFactory(Status.LOGOUT_SUCCESS);
    }
   /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public Result register(User user) {
        TabVendor tabVendor = new TabVendor();
        if (check_name(user)) {//检查用户名是否已经注册
            if (check_phone(user)) {//检查电话是否已经注册
                if (check_email(user)) {//检查邮箱是否已经注册
                    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));//密码加密
                    //设置用户状态为待审核
                    user.setUserStatus("待审核");
                    save(user);
                    return Result.resultFactory(Status.REGISTER_SUCCESS, user);
                } else {
                    return Result.resultFactory(Status.REGISTER_FAIL_EMAIL_HAS, null);
                }
            } else {
                return Result.resultFactory(Status.REGISTER_FAIL_HAS, null);
            }
        } else {
            return Result.resultFactory(Status.REGISTER_FAIL_NAME, null);
        }
    }

    @Override
    public Result userinfo(String token) {
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.resultFactory(Status.STATUS_ERROR);
        }
        //从redis中获取用户信息
        String redisKey = "user:" + userid;
        LoginUser loginUser=redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            return Result.resultFactory(Status.STATUS_ERROR);
        }
        //封装用户信息，不会把用户所有信息返回前端。
        UserInfo userInfo = new UserInfo();
        userInfo.setName(loginUser.getUsername());
        userInfo.setAvatar(loginUser.getUser().getUserImage());
        userInfo.setRoles(loginUser.getPermission());
        userInfo.setIntroduction("介绍");
        return Result.resultFactory(Status.STATUS,userInfo);
    }

    /**
     * 检查用名是否存在
     */
    public boolean check_name(User user) {
        if (getOne(new QueryWrapper<User>().eq("user_name", user.getUserName())) != null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查用户手机是否存在
     */
    public boolean check_phone(User user) {
        if (getOne(new QueryWrapper<User>().eq("user_phone", user.getUserPhone())) != null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查用户邮箱是否存在
     */
    public boolean check_email(User user) {
        if (getOne(new QueryWrapper<User>().eq("user_email", user.getUserEmail())) != null) {
            return false;
        } else {
            return true;
        }
    }
}
