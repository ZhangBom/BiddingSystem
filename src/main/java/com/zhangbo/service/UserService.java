package com.zhangbo.service;

import com.zhangbo.pojo.User;
import com.zhangbo.until.Result;

public interface UserService {
    Result login(User user);

    Result logout();

    Result register(User user);

    Result userinfo(String token);
}
