package com.dacchub.backend.game.repository;

import com.dacchub.backend.game.entity.GameMidia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameMidiaRepository extends JpaRepository<GameMidia, UUID> {
}
