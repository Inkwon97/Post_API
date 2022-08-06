package com.example.postapi.controller;

import com.example.postapi.controller.request.ReplyRequestDto;
import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/api/auth/reply")
    public ResponseDto<?> createReply(@RequestBody ReplyRequestDto requestDto,
                                      HttpServletRequest request){
        return replyService.createReply(requestDto,request);
    }

    @GetMapping("/api/reply/{id}")
    public ResponseDto<?> getAllReplies(@PathVariable Long id){
        return replyService.getAllRepliesByComment(id);
    }

    @PutMapping("/api/auth/reply/{id}")
    public ResponseDto<?> updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto requestDto,
                                      HttpServletRequest request){
        return replyService.updateReply(id, requestDto, request);
    }

    @DeleteMapping("/api/auth/reply/{id}")
    public ResponseDto<?> deleteReply(@PathVariable Long id, HttpServletRequest request){
        return replyService.deleteReply(id, request);
    }

}
