package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}
