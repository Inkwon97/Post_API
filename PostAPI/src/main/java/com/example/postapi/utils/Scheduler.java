package com.example.postapi.utils;

import com.example.postapi.domain.Post;
import com.example.postapi.repository.PostRepository;
import com.example.postapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final PostService postService;
    private final PostRepository postRepository;

    // 초, 분, 시, 일, 월, 주
    @Scheduled(cron = "0 */1 * * * *")
    public void autoDeletePost() throws InterruptedException {
        System.out.println("게시글 삭제 실행");
        // 저장된 모든 게시글 조회
        List<Post> postList = postRepository.findAll();
        for (Post post : postList){
            if (post.getComments().isEmpty()) {
                Long id = post.getId();
                postService.autoDeletePost(id);
            }
        }
    }
}
