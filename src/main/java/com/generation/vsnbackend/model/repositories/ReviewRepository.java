package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
