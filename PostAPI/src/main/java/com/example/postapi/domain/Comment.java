package com.example.postapi.domain;

import javax.persistence.*;

import com.example.postapi.controller.request.CommentHeartRequestDto;
import com.example.postapi.controller.request.CommentRequestDto;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private Long herartCount = 0L;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "post_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reply> replies;

  @Column(nullable = false)
  private String content;

  public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public void addHeart(CommentHeartRequestDto commentHeartRequestDto){
    this.member = commentHeartRequestDto.getMember();
    this.id = commentHeartRequestDto.getComment().getId();
    this.herartCount++;
  }

  public void cancleHeart(CommentHeartRequestDto commentHeartRequestDto) {
    this.member = commentHeartRequestDto.getMember();
    this.id = commentHeartRequestDto.getComment().getId();
    this.herartCount--;
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }
}
