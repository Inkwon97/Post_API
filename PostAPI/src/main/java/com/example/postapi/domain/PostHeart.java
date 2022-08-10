package com.example.postapi.domain;

import com.example.postapi.controller.request.PostHeartDto;
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
public class PostHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id")
//    private Comment comment;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reply_id")
//    private Reply reply;

    // 유저 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public PostHeart(PostHeartDto postHeartDto) {
        this.post = postHeartDto.getPost();
        this.member = postHeartDto.getMember();
    }
}
