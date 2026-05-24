package com.dacchub.backend.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dacchub.backend.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
