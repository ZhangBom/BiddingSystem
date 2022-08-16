package com.zhangbo.service.impl;

import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.mapper.UserMapper;
import com.zhangbo.pojo.LoginUser;
import com.zhangbo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username)
                .or().eq(User::getUserEmail,username)
                .or().eq(User::getUserPhone, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //将用户权限信息封装到UserDetails中
        List<String> list=menuMapper.selectPermsByUserId(user.getUserId());
        return new LoginUser(user,list);
    }
}
