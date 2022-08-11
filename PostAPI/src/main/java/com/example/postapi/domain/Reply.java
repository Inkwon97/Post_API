package com.example.postapi.domain;

import com.example.postapi.controller.request.ReplyHeartRequestDto;
import com.example.postapi.controller.request.ReplyRequestDto;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Reply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private Long herartCount = 0L;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public void update(ReplyRequestDto replyRequestDto){
        this.content = replyRequestDto.getContent();
    }

    public void addHeart(ReplyHeartRequestDto replyHeartRequestDto){
        this.member = replyHeartRequestDto.getMember();
        this.id = replyHeartRequestDto.getReply().getId();
        this.herartCount++;
    }

    public void cancleHeart(ReplyHeartRequestDto replyHeartRequestDto){
        this.member = replyHeartRequestDto.getMember();
        this.id = replyHeartRequestDto.getReply().getId();
        this.herartCount--;
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}
