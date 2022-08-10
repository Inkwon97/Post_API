package com.example.postapi.repository;


import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import com.example.postapi.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Optional<PostHeart> findPostHeartByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);
}
