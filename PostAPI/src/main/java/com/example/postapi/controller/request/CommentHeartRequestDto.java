package com.example.postapi.controller.request;
import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Member;
import lombok.Getter;

@Getter
public class CommentHeartRequestDto {
    private Member member;
    private Comment comment;
}
