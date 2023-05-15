package com.zhangbo.controller;

import com.zhangbo.pojo.TabArticle;
import com.zhangbo.service.ArticleService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("articleModel")
    public Result articleModel(){
       return articleService.articleModel();
    }

    @PostMapping("articleImage")
    public Result articleImage(@RequestParam("file") MultipartFile file){
        return articleService.articleImage(file);
    }

    @GetMapping("get_result_article")
    public Result  get_result_article(){
        return articleService.get_result_article();
    }
}
