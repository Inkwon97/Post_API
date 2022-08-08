package com.example.postapi.repository;

import com.example.postapi.domain.Comment;
import com.example.postapi.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findAllByComment(Comment comment);
}
