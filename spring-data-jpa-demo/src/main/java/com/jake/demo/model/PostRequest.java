package com.jake.demo.model;

import lombok.Data;

@Data
public class PostRequest {
    private String topic;
    private String content;
}
