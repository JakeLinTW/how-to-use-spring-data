package com.jake.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jake.demo.entity.Comment;
import com.jake.demo.entity.Post;
import com.jake.demo.model.CommentDetail;
import com.jake.demo.model.CommentRequest;
import com.jake.demo.model.PostDetail;
import com.jake.demo.model.PostRequest;
import com.jake.demo.model.PostSummary;
import com.jake.demo.repository.CommentJpaRepository;
import com.jake.demo.repository.PostJpaRepository;

@Service
public class ForumService {

    @Autowired
    private CommentJpaRepository commentJpaRepository;
    @Autowired
    private PostJpaRepository postJpaRepository;

    public PostDetail getPostDetail(int postId) {
        Optional<Post> postOpt = postJpaRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();

            List<Comment> commentList = commentJpaRepository.findByPostId(postId);
            List<CommentDetail> commentDetailList = new ArrayList<CommentDetail>();
            commentList.forEach(c -> commentDetailList.add(CommentDetail.builder()
                    .postId(c.getPostId())
                    .content(c.getContent())
                    .commentTime(c.getCreateTime())
                    .build()));

            return PostDetail.builder()
                    .postId(postId)
                    .topic(post.getTopic())
                    .content(post.getContent())
                    .like(post.getLikeCount())
                    .postTime(post.getCreateTime())
                    .commentList(commentDetailList)
                    .build();
        }

        return null;
    }

    public List<PostSummary> getPostList(Pageable page) {
        page = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by("createTime").descending());

        Page<Post> postPage = postJpaRepository.findAll(page);
        List<Post> postList = postPage.getContent();

        List<PostSummary> r = new ArrayList<PostSummary>();

        postList.forEach(p -> r.add(PostSummary.builder()
                .postId(p.getId())
                .topic(p.getTopic())
                .like(p.getLikeCount())
                .postTime(p.getCreateTime())
                .build()));

        return r;
    }

    public boolean createPost(PostRequest req) {
        Post post = new Post();
        post.setTopic(req.getTopic());
        post.setContent(req.getContent());
        postJpaRepository.saveAndFlush(post);
        return true;
    }

    public boolean createComment(CommentRequest req) {
        Comment comment = new Comment();
        comment.setPostId(req.getPostId());
        comment.setContent(req.getContent());
        commentJpaRepository.saveAndFlush(comment);
        return true;
    }

    public boolean like(int postId) {
        Optional<Post> optional = postJpaRepository.findById(postId);
        if (optional.isPresent()) {
            Post post = optional.get();
            post.setLikeCount(post.getLikeCount() + 1);
            postJpaRepository.saveAndFlush(post);
            return true;
        }
        return false;
    }
}
