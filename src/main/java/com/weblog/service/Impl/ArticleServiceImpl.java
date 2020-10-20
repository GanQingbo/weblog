package com.weblog.service.Impl;

import com.weblog.domain.Article;
import com.weblog.domain.User;
import com.weblog.mapper.ArticleMapper;
import com.weblog.mapper.UserMapper;
import com.weblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @author G
 * @version 1.0
 * @date 2020/10/9 16:34
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }
    /**
     * 新建文章
     * @param article
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createArticle(Article article) {
        //获取当前登录用户
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("当前登录用户："+username);
        User user = userMapper.selectByUsername(username);
        article.setUid(user.getId());
        //最后编辑时间
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if(article.getState()==1){
            //发表文章时间
            article.setPublishTime(time);
        }
        article.setEditTime(time);
        //处理文章摘要
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            //直接截取,去除html标签
            String stripHtml = stripHtml(article.getContent());
            //截取字符串作为摘要
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        int i = articleMapper.insertSelective(article);
        return i;
    }
}
