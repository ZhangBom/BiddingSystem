package com.zhangbo.until;

import com.zhangbo.pojo.LoginUser;
import com.zhangbo.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetUser {
    public static User getuser(){
        //获取操作用户
        //获取操作的用户对象
        LoginUser loginUsers = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return loginUsers.getUser();
    }
    public static String getuserid(){
        //获取操作用户
        //获取操作的用户对象
        LoginUser loginUsers = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loginUsers.getUser().getUserId();
    }
}
