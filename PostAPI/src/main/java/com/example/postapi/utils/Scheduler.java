package com.example.postapi.utils;

import com.example.postapi.domain.Post;
import com.example.postapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final PostRepository postRepository;

    // 초, 분, 시, 일, 월, 주
    @Scheduled(cron = "0 0 1 * * *")
    public void autoDeletePost() throws InterruptedException {
        System.out.println("게시글 삭제 실행");
        // 저장된 모든 게시글 조회
        List<Post> postList = postRepository.findAll();
        for (Post post : postList){
            TimeUnit.SECONDS.sleep(1);
            if (post.getComments().isEmpty()) {
                postRepository.delete(post);

                log.info("게시물 <{}>이 삭제되었습니다.", post.getTitle());
            }
        }
    }
}
