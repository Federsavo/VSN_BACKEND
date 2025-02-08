package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.Profile;
import com.generation.vsnbackend.model.entities.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideogameRepository extends JpaRepository<Videogame, Long> {
	List<Videogame> findByProfileAndNameVideogameContainingIgnoreCase(Profile profile, String nameVideogame);
}
