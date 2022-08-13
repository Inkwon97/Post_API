package com.example.postapi.service;

import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import com.example.postapi.domain.PostHeart;
import com.example.postapi.repository.CommentRepository;
import com.example.postapi.repository.MemberRepository;
import com.example.postapi.repository.PostHeartRepository;
import com.example.postapi.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HeartServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostHeartRepository postHeartRepository;
    @Autowired
    CommentRepository commentRepository;

    private Member member1;
    private Member member2;
    private Post post;
    private Comment comment;

    @BeforeEach
    public void setup() {
        member1 = Member.builder()
                .nickname("nickname")
                .password("123123")
                .build();
        memberRepository.save(member1);
        member2 = Member.builder()
                .nickname("nickname1")
                .password("123123")
                .build();
        memberRepository.save(member2);

        post = Post.builder()
                .content("content")
                .title("title")
                .member(member1)
                .build();
        postRepository.save(post);

        comment = Comment.builder()
                .member(member1)
                .post(post)
                .content("content")
                .build();
        commentRepository.save(comment);
    }

    @Test
    @DisplayName("좋아요 추가 기능 테스트")
    @Transactional
    public void addPostHeart() {

        // when
        System.out.println(addPostHeart(1L, member1, post).isSuccess());

        // 중복으로 넣은 경우
        System.out.println(addPostHeart(1L, member2, post).isSuccess());
        System.out.println(addPostHeart(1L, member2, post).isSuccess());

        //then
        int count = postHeartRepository.countByPost(post);
        System.out.println("하트의 개수 : " + count);
        Assertions.assertThat(count).isEqualTo(count);

    }


    @Test
    @DisplayName("좋아요 기능 테스트")
    @Transactional
    public void deletePostHeart() {

        // when
        System.out.println(addPostHeart(1L, member1, post).isSuccess());
        System.out.println("하트의 개수 : " + postHeartRepository.countByPost(post));

        // 중복으로 넣은 경우
        System.out.print(addPostHeart(1L, member2, post).isSuccess());
        System.out.println(" 하트의 개수 : " + postHeartRepository.countByPost(post));
        System.out.print(addPostHeart(1L, member2, post).isSuccess());
        System.out.println(" 하트의 개수 : " + postHeartRepository.countByPost(post));

        // then

        // 삭제한 경우
        System.out.print(deleteHeart(1L, member2, post).isSuccess());
        System.out.println(" 하트의 개수 : " + postHeartRepository.countByPost(post));
        System.out.print(deleteHeart(1L, member2, post).isSuccess());
        System.out.println(" 하트의 개수 : " + postHeartRepository.countByPost(post));

        // 지웠던 멤버가 다시 추가해준 경우
        System.out.println("다시 추가해준 경우");
        System.out.print(addPostHeart(1L, member2, post).isSuccess());
        System.out.println(" 하트의 개수 : " + postHeartRepository.countByPost(post));
    }


    public ResponseDto<?> addPostHeart(Long postId, Member member, Post post) {

        // 만약에 이미 좋아요를 눌렀다면, return
        if (postHeartRepository.findByMemberAndPost(member, post).isPresent()) {
            return ResponseDto.fail("HEART_IS_POSTED", "좋아요가 등록되어 있습니다");
        }

        // 좋아요를 누르지 않았다면, 데이터를 저장
        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .member(member)
                .build();

        postHeartRepository.save(postHeart);


        return ResponseDto.success("좋아요");
    }

    public ResponseDto<?> deleteHeart(Long postId, Member member, Post post) {

        if (!postHeartRepository.findByMemberAndPost(member, post).isPresent()) {

            return ResponseDto.fail("HEART_IS_NOT_POSTED", "하트가 등록되어있지 않습니다");
        }

        // 하트가 있다면
        PostHeart postHeart = postHeartRepository.findByMemberAndPost(member, post).get();

        // member가 등록해준 deleteById(heartId) => member와 Post로 찾아온 Heart가 있다면, 그 Heart를 제거 만약에 Heart가 없다면 fail 발생
        postHeartRepository.deleteById(postHeart.getId());

        return ResponseDto.success("좋아요 취소");
    }

    @Test
    public void PostHeartTest() {


    }
}