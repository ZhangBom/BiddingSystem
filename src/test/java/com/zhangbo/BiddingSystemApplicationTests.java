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
    ApplicationMapper applicationMapper;
    @Autowired
    ScoreMapper scoreMapper;
    @Test
    void text() {
        QueryWrapper<TabApplication> wrapper =new QueryWrapper<>();
        wrapper.eq("purchase_id","102");
        wrapper.ne("expert_account","expert_account");
        int expert_num=applicationMapper.selectCount(wrapper);
//        System.out.println(expert_num);
        QueryWrapper<TabScore> scoreQueryWrapper =new QueryWrapper<>();
        scoreQueryWrapper.eq("purchase_id","102");
        scoreQueryWrapper.eq("vendor_account","1");
        int score_num=scoreMapper.selectCount(scoreQueryWrapper);
        if (expert_num==score_num){
            scoreQueryWrapper.select("sum(vendor_score) as score");
            TabScore tabScore=scoreMapper.selectOne(scoreQueryWrapper);
            QueryWrapper<TabApplication> applicationQueryWrapper = new QueryWrapper<>();
            applicationQueryWrapper.eq("purchase_id","102");
            applicationQueryWrapper.eq("vendor_account", "1");
            //向评分表插入评分
            TabApplication tabApplication = applicationMapper.selectOne(applicationQueryWrapper);
            tabApplication.setVendorScore(tabScore.getScore() / score_num);
            applicationMapper.updateById(tabApplication);
        }
    }
}
