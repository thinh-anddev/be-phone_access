package com.TMDT.api.Api.springboot.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private int productId;
    private int customerId;
    private String content;
    String username;
    String createdAt;
}