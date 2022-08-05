package com.example.postapi.repository;

import java.util.List;
import java.util.Optional;

import com.example.postapi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByOrderByModifiedAtDesc();
}
