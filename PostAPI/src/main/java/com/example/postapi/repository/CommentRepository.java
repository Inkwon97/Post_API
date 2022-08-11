package com.example.postapi.repository;

import java.util.List;

import com.example.postapi.domain.Comment;
import com.example.postapi.domain.Member;
import com.example.postapi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findAllByMember(Member member);
}
