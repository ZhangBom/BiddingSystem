package com.zhangbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbo.mapper.*;
import com.zhangbo.pojo.*;
import com.zhangbo.service.ApplicationService;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.service.ScoreService;
import com.zhangbo.service.UserService;
import com.zhangbo.until.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class BiddingSystemApplicationTests {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Test
    void text() {
        System.out.println(applicationService.sure_score("1"));
        //按时间排序

    }
}
