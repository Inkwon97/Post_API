package com.example.postapi.controller;

import com.example.postapi.controller.request.SubCommentRequestDto;
import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.repository.SubCommentRepository;
import com.example.postapi.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class SubCommentController {

    private final SubCommentService subCommentService;
    private final SubCommentRepository subCommentRepository;

    @GetMapping("/api/auth/{id}/sub-comment")
    public ResponseDto<?> getAllReplyByComment(Long id) {
        // 모든 comment를 return해줘야 함
        return subCommentService.getAllSubCommentByComment(id);
    }

    // reply 작성
    @PostMapping("/api/auth/sub-comment")
    public ResponseDto<?> createSubComment(@RequestBody SubCommentRequestDto requestDto, HttpServletRequest request) {

        return subCommentService.createSubComment(requestDto, request);
    }

    @PutMapping("/api/auth/sub-comment/{id}")
    public ResponseDto<?> updateSubComment(@PathVariable Long Id, @RequestBody SubCommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return subCommentService.updateSubComment(Id, requestDto, request);
    }

    @DeleteMapping("/api/auth/sub-comment/{id}")
    public ResponseDto<?> deleteSubComment(@PathVariable Long Id, HttpServletRequest request) {
        return subCommentService.deleteSubComment(Id, request);
    }
}
