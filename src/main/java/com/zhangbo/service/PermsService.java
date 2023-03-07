package com.zhangbo.service;

import com.zhangbo.pojo.TabRole;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;

public interface PermsService {
    Result role_findAll(PageQuery pageQuery);

    Result role_perms(String role_id);

    Result updateRole(TabRole role);

    Result getMenu();

    Result addRole(TabRole role);
}
