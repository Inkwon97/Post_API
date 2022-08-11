package com.example.postapi.controller.response;

import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Post;
import com.example.postapi.domain.SubComment;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MypageResponseDto {
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<SubComment> subComments = new ArrayList<>();

}
