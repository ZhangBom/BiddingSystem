package com.zhangbo.service;


import com.zhangbo.pojo.TabArticle;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;

public interface ArticleService {
    Result article_add(TabArticle article);

    Result findAll_result(PageQuery pageQuery);
}
