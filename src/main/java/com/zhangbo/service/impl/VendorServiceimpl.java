package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.TableVendor;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;

public class VendorServiceimpl extends ServiceImpl<VendorMapper, TableVendor> implements VendorService{
    @Autowired
    private VendorMapper vendorMapper;
}
