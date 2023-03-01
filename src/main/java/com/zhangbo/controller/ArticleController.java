package com.zhangbo.controller;

import com.zhangbo.pojo.TabArticle;
import com.zhangbo.service.ArticleService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("article_add")
    public Result article_add(@RequestBody TabArticle article){
      return articleService.article_add(article);
    }

    @PostMapping("findAll_result")
    public Result findAll_result(@RequestBody PageQuery pageQuery){
        return articleService.findAll_result(pageQuery);
    }
}
