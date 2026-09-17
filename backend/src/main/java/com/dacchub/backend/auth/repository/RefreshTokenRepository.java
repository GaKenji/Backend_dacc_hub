package com.dacchub.backend.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dacchub.backend.auth.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
  Optional<RefreshToken> findById(UUID id);

  @Transactional
  @Modifying
  @Query("UPDATE RefreshToken r SET r.loggedOut = true WHERE r.user.email = :email AND r.loggedOut = false")
  int revokeAllByUserEmail(@Param("email") String email);
}
