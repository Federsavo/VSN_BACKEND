package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
