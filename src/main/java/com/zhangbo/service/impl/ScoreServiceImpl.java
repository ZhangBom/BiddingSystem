package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ApplicationMapper;
import com.zhangbo.mapper.ScoreMapper;
import com.zhangbo.pojo.TabApplication;
import com.zhangbo.pojo.TabScore;
import com.zhangbo.service.ScoreService;
import com.zhangbo.until.GetUser;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, TabScore> implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    ApplicationMapper applicationMapper;

    @Override
    public Result vendorScore(TabScore score) {
        //查找是否已经有评分
        QueryWrapper<TabScore> wrapper = new QueryWrapper<>();
        wrapper.eq("expert_account", GetUser.getuserid());
        wrapper.eq("purchase_id", score.getPurchaseId());
        wrapper.eq("vendor_account", score.getVendorAccount());
        TabScore score1 = scoreMapper.selectOne(wrapper);
        if (Objects.isNull(score1)) {
            score.setExpertAccount(GetUser.getuserid());
            save(score);
            check_score(score.getPurchaseId(), score.getVendorAccount());
            return Result.resultFactory(Status.SCORE_SUCCESS);
        } else {
            score1.setVendorScore(score.getVendorScore());
            updateById(score1);
            check_score(score.getPurchaseId(), score.getVendorAccount());
            return Result.resultFactory(Status.SCORE_UPDATE_SUCCESS);
        }
    }

    /**
     * 获得这个专家对这个项目所有参标公司的评分
     * @param purchase_id
     * @param vendor_account
     * @return
     */
    @Override
    public Result get_score(String purchase_id, String vendor_account) {
        QueryWrapper<TabScore> wrapper = new QueryWrapper<>();
        wrapper.eq("expert_account", GetUser.getuserid());
        wrapper.eq("purchase_id", purchase_id);
        wrapper.eq("vendor_account", vendor_account);
        TabScore score = scoreMapper.selectOne(wrapper);
        if (Objects.isNull(score)) {
            return Result.resultFactory(Status.SCORE_SUCCESS, 0);
        } else {
            return Result.resultFactory(Status.STATUS, score.getVendorScore());
        }
    }

    /**
     * 每次评分都会查询是否全部完成评分，是的话向报名表插入平均评分，否则不做操作
     * @param purchase_id
     * @param vendor_account
     */
    public void check_score(String purchase_id, String vendor_account) {
        QueryWrapper<TabApplication> wrapper = new QueryWrapper<>();
        wrapper.eq("purchase_id", purchase_id);
        wrapper.ne("expert_account", "expert_account");
        int expert_num = applicationMapper.selectCount(wrapper);
        QueryWrapper<TabScore> scoreQueryWrapper = new QueryWrapper<>();
        scoreQueryWrapper.eq("purchase_id", purchase_id);
        scoreQueryWrapper.eq("vendor_account", vendor_account);
        int score_num = scoreMapper.selectCount(scoreQueryWrapper);
        if (expert_num == score_num) {
            scoreQueryWrapper.select("sum(vendor_score) as score");
            TabScore tabScore = scoreMapper.selectOne(scoreQueryWrapper);
            QueryWrapper<TabApplication> applicationQueryWrapper = new QueryWrapper<>();
            applicationQueryWrapper.eq("purchase_id", purchase_id);
            applicationQueryWrapper.eq("vendor_account", vendor_account);
            //向评分表插入评分
            TabApplication tabApplication = applicationMapper.selectOne(applicationQueryWrapper);
            tabApplication.setVendorScore(tabScore.getScore() / score_num);
            applicationMapper.updateById(tabApplication);
        }
    }
}
