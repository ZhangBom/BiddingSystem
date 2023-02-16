package com.zhangbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.mapper.StructureMapper;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.TabStructure;
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
    @Autowired
    private StructureMapper structureMapper;
    @Test
    void text() {
        HumpUntil humpUntil = new HumpUntil();
        String a = "ascuserId";
        a= humpUntil.hump_underline(a);
        System.out.println(a);
        System.out.println(a.substring(3));
    }
}
