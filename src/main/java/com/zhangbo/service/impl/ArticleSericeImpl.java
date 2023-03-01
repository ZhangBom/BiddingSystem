package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ArticleMapper;
import com.zhangbo.until.BackPage;
import com.zhangbo.until.PageQuery;
import com.zhangbo.pojo.TabArticle;
import com.zhangbo.service.ArticleService;
import com.zhangbo.until.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ArticleSericeImpl extends ServiceImpl<ArticleMapper, TabArticle> implements ArticleService {

    @Override
    public Result article_add(TabArticle article) {
        save(article);
        return Result.resultFactory(Status.ADD_INFO_SUCCESS);
    }

    @Override
    public Result findAll_result(PageQuery pageQuery) {
        System.out.println(pageQuery);
        BackPage backPage=new BackPage();
        QueryWrapper<TabArticle> wrapper=new QueryWrapper<>();
        wrapper.eq("article_type",pageQuery.getArticleType());
        if (StringUtils.isNotEmpty(pageQuery.getConditions())) {
            HumpUntil humpUntil = new HumpUntil();
            wrapper.like(humpUntil.hump_underline(pageQuery.getConditions()), pageQuery.getTitle());
        }
        wrapper.orderByDesc("article_time");
        System.out.println(wrapper);
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabArticle> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabArticle> postIPage = page(postPage, wrapper);
        //封装返回格式
        backPage.setContentList(postIPage.getRecords());
        backPage.setCurrentPage(postIPage.getCurrent());
        backPage.setTotalPage(postIPage.getPages());
        backPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, backPage);
    }
}
