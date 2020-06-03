package com.jake.demo.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostDetail {
    private int postId;
    private String topic;
    private String content;
    private int like;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;
    private List<CommentDetail> commentList;
}
