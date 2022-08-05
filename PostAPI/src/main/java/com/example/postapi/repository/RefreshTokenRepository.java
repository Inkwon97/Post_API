package com.example.postapi.repository;

import java.util.Optional;

import com.example.postapi.domain.Member;
import com.example.postapi.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}
