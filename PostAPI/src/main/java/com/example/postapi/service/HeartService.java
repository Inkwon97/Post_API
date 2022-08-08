package com.example.postapi.service;

import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import com.example.postapi.domain.PostHeart;
import com.example.postapi.jwt.TokenProvider;
import com.example.postapi.repository.PostHeartRepository;
import com.example.postapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {


    private final PostRepository postRepository;
    private final TokenProvider tokenProvider;
    private final PostHeartRepository postHeartRepository;

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public ResponseDto<?> addHeart(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        // 만약에 이미 좋아요를 눌렀다면, 좋아요를 제거
        if (postHeartRepository.findByMemberAndPost(member, post).isPresent()) {
//            postHeartRepository.delete(postHeartRepository.findByMemberAndPost(member, post).get());
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

    public ResponseDto<?> deleteHeart(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (!postHeartRepository.findByMemberAndPost(member, post).isPresent()) {

            return ResponseDto.fail("HEART_IS_NOT_POSTED", "하트가 등록되어있지 않습니다");
        }

        // 하트가 있다면
        PostHeart postHeart = postHeartRepository.findByMemberAndPost(member, post).get();

        // member가 등록해준 deleteById(heartId) => member와 Post로 찾아온 Heart가 있다면, 그 Heart를 제거 만약에 Heart가 없다면 fail 발생
        postHeartRepository.deleteById(postHeart.getId());

        return ResponseDto.success("좋아요 취소");
    }
}
