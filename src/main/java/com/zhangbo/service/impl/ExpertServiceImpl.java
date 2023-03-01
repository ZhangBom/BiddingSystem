package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ExpertMapper;
import com.zhangbo.until.BackPage;
import com.zhangbo.until.PageQuery;
import com.zhangbo.pojo.TabExpert;
import com.zhangbo.service.ExpertService;
import com.zhangbo.until.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceImpl extends ServiceImpl<ExpertMapper, TabExpert> implements ExpertService {
    @Autowired
    private ExpertMapper expertMapper;
    @Override
    public Result findAll(PageQuery pageQuery) {
        BackPage<TabExpert> tabExpertBackPage = new BackPage<>();
        //构建查询条件
        QueryWrapper<TabExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("expert_status", "待审核");
        if (StringUtils.isNotEmpty(pageQuery.getConditions())) {
            HumpUntil humpUntil = new HumpUntil();
            wrapper.eq(humpUntil.hump_underline(pageQuery.getConditions()), pageQuery.getTitle());
        }
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabExpert> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());
        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabExpert> postIPage = page(postPage, wrapper);
        //封装返回格式
        tabExpertBackPage.setContentList(postIPage.getRecords());
        tabExpertBackPage.setCurrentPage(postIPage.getCurrent());
        tabExpertBackPage.setTotalPage(postIPage.getPages());
        tabExpertBackPage.setTotalNum(postIPage.getTotal());
        //封装返回格式
        return Result.resultFactory(Status.SUCCESS, tabExpertBackPage);
    }

    @Override
    public Result expert_update(TabExpert expert) {
        updateById(expert);
        return Result.resultFactory(Status.DELETE_INFO_SUCCESS);
    }
    @Override
    public Result getInfo() {
        QueryWrapper<TabExpert> wrapper = new QueryWrapper<>();
        wrapper.eq("expert_account",GetUser.getuserid());
        TabExpert expert = expertMapper.selectOne(wrapper);
        return Result.resultFactory(Status.SUCCESS, expert);
    }
}
