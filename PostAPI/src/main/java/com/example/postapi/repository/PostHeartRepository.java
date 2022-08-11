package com.example.postapi.repository;

import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import com.example.postapi.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Optional<PostHeart> findByMemberAndPost(Member member, Post post);
    int countByPost(Post post);

    List<PostHeart> findAllByMember(Member member);

    Optional<PostHeart> findPostHeartByMemberAndPost(Member member, Post post);
    void deleteByMemberAndPost(Member member, Post post);
}
