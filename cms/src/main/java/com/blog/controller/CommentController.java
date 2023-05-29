package com.blog.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageInfo;
import com.blog.entity.Article;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.framework.mvc.UserInfoShareHolder;
import com.blog.service.impl.ArticleServiceImpl;
import com.blog.service.impl.CommentServiceImpl;
import com.blog.service.impl.UserServiceImpl;
import com.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zhaoguoshun
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @PostMapping("/delete")
    public Result delete(Integer id){
        commentServiceImpl.delete(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody  Comment comment){
        commentServiceImpl.update(comment);
        return Result.ok(comment);
    }
    @PostMapping("/query")
    public Map query(@RequestBody Comment comment){
        Integer user_id = UserInfoShareHolder.getUserInfo().getId();
        User user = userServiceImpl.selectOne(user_id);
        if (user.getAuthority() == 1){
            Article article = new Article();
            article.setCreateUser(user_id);
            PageInfo<Article> pageInfo = articleServiceImpl.query(article);
            List<Integer> article_ids = new ArrayList<>();
            if (CollectionUtil.isEmpty(pageInfo.getList())) {
                PageInfo page = new PageInfo();
                return Result.ok(page);
            }
            for (Article art: pageInfo.getList()) {
                article_ids.add(art.getId());
            }
            PageInfo<Comment> page = commentServiceImpl.queryByIds(article_ids);
            return Result.ok(page);

        }else{
            PageInfo<Comment> page = commentServiceImpl.query(comment);
            return Result.ok(page);
        }
//        System.out.println(page.getList());
//        return Result.ok(page);
    }


    @PostMapping("/detail")
    public Result detail(Integer id){
        Comment detail = commentServiceImpl.detail(id);
        return Result.ok(detail);
    }

    @PostMapping("/count")
    public Result count(@RequestBody Comment comment){
        int count = commentServiceImpl.count(comment);
        return Result.ok(count);
    }

    /**
     * 查询出所有状态为1的评论
     * @return
     */
    @PostMapping("/getStatusComment")
    public Map getStatusComment(Comment comment){
        System.out.println("评论的内容"+comment);
        comment.setStatus(1);
        PageInfo<Comment> pageInfo = commentServiceImpl.query(comment);
        System.out.println(pageInfo.getList());
        return Result.ok(pageInfo);
    }

    //分页查询出所有状态为1的评论
    @GetMapping("/getPageCommentId")
    public Map getStatusComment(Integer pageId){
        Comment comment = new Comment();
        System.out.println("评论的内容"+pageId);
        comment.setStatus(1);
        comment.setPage(pageId);
        PageInfo<Comment> pageInfo = commentServiceImpl.query(comment);
        System.out.println(pageInfo.getList());
        return Result.ok(pageInfo);
    }

    /**
     * 审核通过
     */

    @GetMapping("/getUpdateStatus")
    public Result getUpdateStatus(Integer commentId){
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setStatus(0);
        commentServiceImpl.update(comment);
        return Result.ok();
    }

}

