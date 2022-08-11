package com.example.postapi.controller.request;

import com.example.postapi.domain.Member;
import com.example.postapi.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReplyHeartRequestDto {
    private Member member;
    private Reply reply;
}
