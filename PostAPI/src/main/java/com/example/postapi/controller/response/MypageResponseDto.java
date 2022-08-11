package com.example.postapi.controller.response;

import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Post;
import com.example.postapi.domain.Reply;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MypageResponseDto {
    List<Post> posts = new ArrayList<>();
    List<Comment> comments = new ArrayList<>();
    List<Reply> replies = new ArrayList<>();
}
