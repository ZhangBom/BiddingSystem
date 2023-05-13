package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.OutcomeMapper;
import com.zhangbo.pojo.TabOutcome;
import com.zhangbo.service.ContactService;
import com.zhangbo.until.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends ServiceImpl<OutcomeMapper, TabOutcome> implements ContactService {
    @Override
    public Result findAll(PageQuery pageQuery) {
        BackPage<TabOutcome> backPage = new BackPage<>();
        //构建查询条件
        QueryWrapper<TabOutcome> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(pageQuery.getConditions())) {
            HumpUntil humpUntil = new HumpUntil();
            wrapper.like(humpUntil.hump_underline(pageQuery.getConditions()), pageQuery.getTitle());
        }
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabOutcome> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabOutcome> postIPage = page(postPage, wrapper);
        //封装返回格式
        backPage.setContentList(postIPage.getRecords());
        backPage.setCurrentPage(postIPage.getCurrent());
        backPage.setTotalPage(postIPage.getPages());
        backPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, backPage);
    }
}
