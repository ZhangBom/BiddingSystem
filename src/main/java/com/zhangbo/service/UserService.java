package com.zhangbo.service;

import com.zhangbo.pojo.User;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Params;
import com.zhangbo.until.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UserService {
    Result login(User user);

    Result logout();

    Result register(User user);

    Result userinfo();

    Result findAll(PageQuery pageQuery);

    Result banuser(User user);

    Result user_avatar(MultipartFile file);

    Result checkbox();

    Result updatepass(Params params);


}
