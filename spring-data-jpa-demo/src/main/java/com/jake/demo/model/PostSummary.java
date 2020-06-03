package com.jake.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostSummary {
    private int postId;
    private String topic;
    private int like;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;
}
