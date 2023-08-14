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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Map;

@SpringBootTest
class BiddingSystemApplicationTests {
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private UserService service;
    @Test
    void text() {
        String purchase_id="102";
        String where = "count(expert_account)";
        QueryWrapper<TabScore> wrapper = new QueryWrapper<>();
        wrapper.eq("purchase_id", purchase_id);
        wrapper.groupBy("expert_account");
        wrapper.select(where);
        System.out.println(scoreMapper.selectList(wrapper));
        //按时间排序
    }

}
