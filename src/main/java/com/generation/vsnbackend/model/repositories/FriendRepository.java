package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
