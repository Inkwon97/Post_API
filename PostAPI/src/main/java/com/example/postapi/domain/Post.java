package com.example.postapi.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.postapi.controller.request.PostHeartRequestDto;
import com.example.postapi.controller.request.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column
  private Long heartCount = 0L;


  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
  @Column(nullable = false)
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();

  private String imageUrl;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  public void update(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
  }

  public void addHeart(PostHeartRequestDto postHeartRequestDto){
    this.member = postHeartRequestDto.getMember();
    this.id = postHeartRequestDto.getPost().getId();
    this.heartCount++;
//    new PostHeart(postHeartRequestDto);
  }

  public void cancleHeart(PostHeartRequestDto postHeartRequestDto){
    this.member = postHeartRequestDto.getMember();
    this.id = postHeartRequestDto.getPost().getId();
    this.heartCount--;
//    new PostHeart(postHeartRequestDto);
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

}
