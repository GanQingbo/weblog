package com.weblog.controller;

import com.weblog.domain.Article;
import com.weblog.service.Impl.ArticleServiceImpl;
import com.weblog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author G
 * @version 1.0
 * @date 2020/10/9 16:43
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleServiceImpl articleServiceImpl;
    @Autowired
    Result result;
    @RequestMapping("/create")
    public Result createArticle(Article article){
        System.out.println(article);
        int i = articleServiceImpl.createArticle(article);
        if(i>0){
            result.setMsg("新增文章成功");
        }else {
            result.setMsg("新增文章失败");
        }
        return result;
    }
}
