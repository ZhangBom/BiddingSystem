package com.zhangbo.service;

import com.zhangbo.pojo.TabSuggestion;
import com.zhangbo.pojo.User;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Params;
import com.zhangbo.until.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

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


    Result user_update(Params params);


    Result suggestion(String textarea);

    Result get_suggestion(PageQuery pageQuery);

    Result deal_Suggestion(TabSuggestion suggestion);

    Result userCount();

    Result suggestionCount();

    Result get_user_moth_num();

    Result get_suggestion_moth_num();
}
