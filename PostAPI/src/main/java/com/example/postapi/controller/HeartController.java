package com.example.postapi.controller;

import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.domain.Post;
import com.example.postapi.repository.PostHeartRepository;
import com.example.postapi.repository.PostRepository;
import com.example.postapi.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;
    private final PostHeartRepository postHeartRepository;
    private final PostRepository postRepository;

    // 게시글 좋아요
    @PostMapping("/api/{postId}/heart")
    public ResponseDto<?> AddpostHeart(@PathVariable Long postId, HttpServletRequest request) {
        return heartService.addHeart(postId, request);
//        return new ResponseEntity<>(postHeartRequestDto, HttpStatus.CREATED);
    }

    // 게시글 좋아요 취소
    @DeleteMapping("/api/{postId}/heart")
    public ResponseDto<?> deletePostHeart(@PathVariable Long postId, HttpServletRequest request) {
        return heartService.deleteHeart(postId, request);
    }

    // 게시글 좋아요 개수 반환
    @GetMapping("/api/{postId}/heart")
    public int getPostHeart(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).get();
        return postHeartRepository.countByPost(post);
    }

}
