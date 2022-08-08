package com.example.postapi.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCommentRequestDto {
    private Long commentId;
    private String content;

    public SubCommentRequestDto(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
