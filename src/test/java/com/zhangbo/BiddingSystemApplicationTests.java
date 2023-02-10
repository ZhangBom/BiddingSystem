package com.zhangbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.TabVendor;
import com.zhangbo.until.COSUtil;
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

    @Test
    void text() {
        System.out.println(COSUtil.deletefile("purchasefile/17241675944973473.7z"));
    }

}
