package com.example.postapi.controller.response;

import com.example.postapi.domain.PostHeart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MyHeartResponseDto {
    List<PostHeart> postHearts = new ArrayList<>();
}
