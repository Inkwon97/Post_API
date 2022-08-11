package com.example.postapi.domain;

import com.example.postapi.controller.request.CommentHeartRequestDto;
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
public class CommentHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public CommentHeart(CommentHeartRequestDto commentHeartRequestDto) {
        this.member = commentHeartRequestDto.getMember();
        this.comment = commentHeartRequestDto.getComment();
    }
}
