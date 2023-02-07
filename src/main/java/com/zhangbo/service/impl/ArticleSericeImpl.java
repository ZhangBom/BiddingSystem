package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ArticleMapper;
import com.zhangbo.pojo.TabArticle;
import com.zhangbo.service.ArticleService;
import com.zhangbo.service.ExpertService;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleSericeImpl extends ServiceImpl<ArticleMapper, TabArticle> implements ArticleService {
    @Autowired
    private ExpertService expertService;
    @Override
    public Result article_add(TabArticle article) {
        save(article);
        return Result.resultFactory(Status.ADD_INFO_SUCCESS, "成功");
    }
}
