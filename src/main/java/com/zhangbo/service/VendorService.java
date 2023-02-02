package com.zhangbo.service;

import com.zhangbo.until.PurchaseQuery;
import com.zhangbo.until.Result;

public interface VendorService {
    Result findAll(PurchaseQuery purchaseQuery);
}
