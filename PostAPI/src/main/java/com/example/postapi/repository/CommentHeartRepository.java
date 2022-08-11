package com.example.postapi.repository;

import com.example.postapi.domain.Comment;
import com.example.postapi.domain.CommentHeart;
import com.example.postapi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Optional<CommentHeart> findCommentHeartByMemberAndComment(Member member, Comment comment);
    void deleteByMemberAndComment(Member member, Comment comment);
}
