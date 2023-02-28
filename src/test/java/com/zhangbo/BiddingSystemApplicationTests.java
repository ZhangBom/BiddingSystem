package com.zhangbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.mapper.StructureMapper;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.TabStructure;
import com.zhangbo.pojo.TabVendor;
import com.zhangbo.pojo.User;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.service.UserService;
import com.zhangbo.until.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class BiddingSystemApplicationTests {

    @Test
    void text() {
       DateDiff dateDiff=new DateDiff();
       String str="2023 年 03 月 17 日";
//      dateDiff.DateWeekend("2023 年 03 月 17 日");
        str = str.replaceAll("\\s*","");
        System.out.println(dateDiff.getNow());
//       application.setApplicationTime(dateDiff.getNow());

    }
}
