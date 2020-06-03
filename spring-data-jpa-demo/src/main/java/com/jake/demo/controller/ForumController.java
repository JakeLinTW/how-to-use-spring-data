package com.jake.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jake.demo.model.CommentRequest;
import com.jake.demo.model.PostDetail;
import com.jake.demo.model.PostRequest;
import com.jake.demo.model.PostSummary;
import com.jake.demo.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/{postId}")
    public PostDetail getPostDetail(@PathVariable int postId) {
        return forumService.getPostDetail(postId);
    }

    @GetMapping("/list")
    public List<PostSummary> getPostList(Pageable page) {
        return forumService.getPostList(page);
    }

    @PostMapping("/post")
    public boolean createPost(@RequestBody PostRequest post) {
        forumService.createPost(post);
        return true;
    }

    @PostMapping("/comment")
    public boolean createComment(@RequestBody CommentRequest comment) {
        forumService.createComment(comment);
        return true;
    }

    @PostMapping("/like/{postId}")
    public boolean like(@PathVariable int postId) {
        forumService.like(postId);
        return true;
    }
}
