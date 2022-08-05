package com.example.postapi.controller.request;

import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import lombok.Getter;

@Getter
public class PostHeartRequestDto {
    private Member member;
    private Post post;
}
