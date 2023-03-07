package com.zhangbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhangbo.mapper.*;
import com.zhangbo.pojo.*;
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
MenuMapper mapper;
    @Test
    void text() {
        System.out.println(mapper.select_role_menu(6));
    }
}
