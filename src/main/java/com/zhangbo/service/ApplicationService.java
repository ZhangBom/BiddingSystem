package com.zhangbo.service;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.until.Result;

public interface ApplicationService {
    Result signpurchase(TabPurchase purchase);
}
