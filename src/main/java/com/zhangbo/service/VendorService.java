package com.zhangbo.service;

import com.zhangbo.pojo.TabVendor;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;

public interface VendorService {
    Result findAll(PageQuery pageQuery);

    Result find_vendor_level();

    Result find_vendor_type();

    Result vendor_update(TabVendor vendor);
}
