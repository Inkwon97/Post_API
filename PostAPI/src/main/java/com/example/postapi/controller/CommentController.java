package com.example.postapi.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.postapi.controller.request.CommentRequestDto;
import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

  private final CommentService commentService;

  @RequestMapping(value = "/api/auth/comment", method = RequestMethod.POST)
  public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                      HttpServletRequest request) {
    return commentService.createComment(requestDto, request);
  }

  @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getAllComments(@PathVariable Long id) {
    return commentService.getAllCommentsByPost(id);
  }
/*
  @GetMapping("/api/comment/{id}/{id}")
  public ResponseDto<?> getComment(@PathVariable Long postId, Long id){
    return commentService.getComment(postId, id);
  }
*/
  @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
      HttpServletRequest request) {
    return commentService.updateComment(id, requestDto, request);
  }

  @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deleteComment(@PathVariable Long id,
      HttpServletRequest request) {
    return commentService.deleteComment(id, request);
  }

  @GetMapping("/api/auth/comment/heart/{id}")
  public ResponseDto<?> addCommentHeart(@PathVariable Long id, HttpServletRequest request){
    return commentService.addCommnetHeart(id, request);
  }
}
