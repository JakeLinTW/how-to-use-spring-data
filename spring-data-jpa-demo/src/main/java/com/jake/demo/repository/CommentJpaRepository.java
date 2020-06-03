package com.jake.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jake.demo.entity.Comment;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPostId(int postId);
}
