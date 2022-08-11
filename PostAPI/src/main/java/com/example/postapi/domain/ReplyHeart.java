package com.example.postapi.domain;

import com.example.postapi.controller.request.ReplyHeartRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public ReplyHeart(ReplyHeartRequestDto replyHeartRequestDto) {
        this.member = replyHeartRequestDto.getMember();
        this.reply = replyHeartRequestDto.getReply();
    }
}
