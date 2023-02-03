package com.zhangbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.TabVendor;
import com.zhangbo.until.HumpUntil;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class BiddingSystemApplicationTests {
    @Autowired
    private VendorMapper vendorMapper;

    @Test
    void text() {
        QueryWrapper<TabVendor> wrapper=new QueryWrapper<>();
        wrapper.select("vendor_level").groupBy("vendor_level");
        List<TabVendor> list=vendorMapper.selectList(wrapper);
    }

}
