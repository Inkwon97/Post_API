package com.example.postapi.controller;

import com.example.postapi.controller.request.PostRequestDto;
import com.example.postapi.controller.response.ResponseDto;
import com.example.postapi.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostController {

  private final PostService postService;

  @RequestMapping(value = "/api/auth/post", method = RequestMethod.POST)
  public ResponseDto<?> createPost(@RequestParam("dto") String dto, @RequestParam("images") MultipartFile images,
                                   HttpServletRequest request) throws IOException {
    PostRequestDto requestDto  = new ObjectMapper().readValue(dto, PostRequestDto.class);
    return postService.createPost(requestDto, images, request);
  }

  @RequestMapping(value = "/api/post/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getPost(@PathVariable Long id) {
    return postService.getPost(id);
  }

  @RequestMapping(value = "/api/post", method = RequestMethod.GET)
  public ResponseDto<?> getAllPosts() {
    return postService.getAllPost();
  }

  @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
      HttpServletRequest request) {
    return postService.updatePost(id, postRequestDto, request);
  }

  @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deletePost(@PathVariable Long id,
      HttpServletRequest request) {
    return postService.deletePost(id, request);
  }

  @GetMapping("/api/auth/post/heart/{id}")
  public ResponseDto<?> addPostHeart(@PathVariable Long id, HttpServletRequest request){
    return postService.addPostHeart(id, request);
  }

}
