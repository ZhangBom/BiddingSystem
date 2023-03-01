package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.pojo.Menu;
import com.zhangbo.pojo.TabSuggestion;
import com.zhangbo.service.PermsService;
import com.zhangbo.until.BackPage;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermsServiceImpl extends ServiceImpl<MenuMapper,Menu> implements PermsService {
    @Autowired
    private MenuMapper mapper;
    @Override
    public Result role_findAll(PageQuery pageQuery) {
        BackPage<Menu> backPage = new BackPage<>();
//        QueryWrapper<Menu> wrapper=new QueryWrapper<>();
        Page<Menu> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());
        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<Menu> postIPage = mapper.selectPage(postPage,null);
        //封装返回格式
        backPage.setContentList(postIPage.getRecords());
        backPage.setCurrentPage(postIPage.getCurrent());
        backPage.setTotalPage(postIPage.getPages());
        backPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, backPage);
    }
}
