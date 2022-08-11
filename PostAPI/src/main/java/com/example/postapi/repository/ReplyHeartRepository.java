package com.example.postapi.repository;

import com.example.postapi.domain.Member;
import com.example.postapi.domain.Reply;
import com.example.postapi.domain.ReplyHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyHeartRepository extends JpaRepository<ReplyHeart, Long> {
    Optional<ReplyHeart> findReplyHeartByMemberAndReply(Member member, Reply reply);
    void deleteByMemberAndReply(Member member, Reply reply);
}
