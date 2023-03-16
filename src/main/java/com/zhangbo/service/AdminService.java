package com.zhangbo.service;

import com.zhangbo.pojo.TabAdmin;
import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.until.Result;

public interface AdminService {
    Result getInfo();

    Result register(TabAdmin admin);
}
