package com.example.postapi.controller.request;

import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostHeartDto {
    private Member member;
    private Post post;
}
