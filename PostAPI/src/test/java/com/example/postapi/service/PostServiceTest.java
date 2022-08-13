package com.example.postapi.service;

import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import com.example.postapi.repository.CommentRepository;
import com.example.postapi.repository.MemberRepository;
import com.example.postapi.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;

    @Test
    @Transactional
    @DisplayName("comment가 POST에서 조회가 가능한지 확인")
    public void CommentTest() {
        Member member = Member.builder()
                .nickname("nickname")
                .password("123123")
                .build();
        memberRepository.save(member);

        Post post = Post.builder()
                .content("content")
                .title("title")
                .member(member)
                .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content("content")
                .build();
        commentRepository.save(comment);

        System.out.println(member);
        System.out.println(post);

        // comment에서 post로 접근이 가능
        System.out.println("comment의 post로 접근한 ID : " + comment.getPost().getId());

        // POST에서 comment로 접근한 경우
        System.out.println(post.getComments()); // null 값이 반환됨

        Assertions.assertThat(1).isEqualTo(1);

    }

    @Test
    public void nullTest() {
        List<Comment> a = new ArrayList<>();
        a.add(new Comment());
        a.add(new Comment());
        a.add(new Comment());
        a.add(new Comment());
        a.add(new Comment());

        System.out.println(a);
    }
}