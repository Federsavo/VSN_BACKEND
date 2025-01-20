package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
