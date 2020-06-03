package com.jake.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jake.demo.entity.Post;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Integer> {
}
