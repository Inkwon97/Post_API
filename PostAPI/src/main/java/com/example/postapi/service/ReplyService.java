package com.example.postapi.service;

import com.example.postapi.controller.request.ReplyHeartRequestDto;
import com.example.postapi.controller.request.ReplyRequestDto;
import com.example.postapi.controller.response.ReplyResponseDto;
import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Member;
import com.example.postapi.domain.Reply;
import com.example.postapi.domain.ReplyHeart;
import com.example.postapi.jwt.TokenProvider;
import com.example.postapi.repository.ReplyHeartRepository;
import com.example.postapi.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final TokenProvider tokenProvider;
    private final CommentService commentService;
    private final ReplyHeartRepository replyHeartRepository;

    @Transactional
    public ResponseDto<?> createReply(ReplyRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment){
            return ResponseDto.fail("NOT_FOUND", "존재하지 않은 댓글입니다.");
        }

        Reply reply = Reply.builder()
                .member(member)
                .comment(comment)
                .content(requestDto.getContent())
                .herartCount(0L)
                .build();
        replyRepository.save(reply);

        return ResponseDto.success(
                ReplyResponseDto.builder()
                        .id(reply.getId())
                        .author(reply.getMember().getNickname())
                        .content(reply.getContent())
                        .heartCount(reply.getHerartCount())
                        .createdAt(reply.getCreatedAt())
                        .modifiedAt(reply.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> getAllRepliesByComment(Long commentId) {
        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment){
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        List<Reply> replyList = replyRepository.findAllByComment(comment);
        List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();

        for (Reply reply : replyList){
            replyResponseDtoList.add(
                    ReplyResponseDto.builder()
                            .id(reply.getId())
                            .author(reply.getMember().getNickname())
                            .content(reply.getContent())
                            .heartCount(reply.getHerartCount())
                            .createdAt(reply.getCreatedAt())
                            .modifiedAt(reply.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(replyResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> updateReply(Long id, ReplyRequestDto requestDto, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        Reply reply = isPresentReply(id);
        if (null == reply){
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (reply.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        reply.update(requestDto);
        return ResponseDto.success(
                ReplyResponseDto.builder()
                        .id(reply.getId())
                        .author(reply.getMember().getNickname())
                        .content(reply.getContent())
                        .heartCount(reply.getHerartCount())
                        .createdAt(reply.getCreatedAt())
                        .modifiedAt(reply.getModifiedAt())
                        .build()
        );
    }

    public ResponseDto<?> deleteReply(Long id, HttpServletRequest request) {
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

        Reply reply = isPresentReply(id);
        if (null == reply){
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        if (reply.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        replyRepository.delete(reply);

        return ResponseDto.success("success");
    }

    @Transactional
    public ResponseDto<?> addReplyHeart(Long id, HttpServletRequest request){
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

        Reply reply = isPresentReply(id);
        if (null == reply){
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        ReplyHeartRequestDto replyHeartRequestDto = new ReplyHeartRequestDto(member, reply);

        //좋아요 상태에서 한번 더 요청을 보내면 취소
        if (replyHeartRepository.findReplyHeartByMemberAndReply(member,reply).isPresent()){
            replyHeartRepository.deleteByMemberAndReply(member,reply);
            reply.cancleHeart(replyHeartRequestDto);

            return ResponseDto.success("좋아요 취소!");
        }

        reply.addHeart(replyHeartRequestDto);
        ReplyHeart replyHeart = new ReplyHeart(replyHeartRequestDto);
        replyHeartRepository.save(replyHeart);

        return ResponseDto.success("좋아요 완료!");
    }

    @Transactional
    public Reply isPresentReply(Long id){
        Optional<Reply> optionalReply = replyRepository.findById(id);
        return optionalReply.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
