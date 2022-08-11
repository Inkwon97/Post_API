package com.example.postapi.controller.request;
import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentHeartRequestDto {
    private Member member;
    private Comment comment;
}
