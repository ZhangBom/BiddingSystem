package com.zhangbo.service.impl;

import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.mapper.UserMapper;
import com.zhangbo.pojo.LoginUser;
import com.zhangbo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException{

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username)
                .or().eq(User::getUserEmail,username)
                .or().eq(User::getUserPhone, username));
        //如果为空返回
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误,请重新登录");
        }
        else if(!user.getUserStatus().equals("0")){
            throw new DisabledException("账号被封禁，或修改信息后审核未通过，请联系管理员！");
        }else {
            //将用户权限信息封装到UserDetails中
            List<String> list = menuMapper.selectPermsByUserId(user.getUserId());
            return new LoginUser(user, list);
        }
    }
}
