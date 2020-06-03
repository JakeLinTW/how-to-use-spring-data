package com.jake.demo.model;

import lombok.Data;

@Data
public class CommentRequest {
    private int postId;
    private String content;
}
