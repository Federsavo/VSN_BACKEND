package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRepository extends JpaRepository<Videogame, Long> {
}
