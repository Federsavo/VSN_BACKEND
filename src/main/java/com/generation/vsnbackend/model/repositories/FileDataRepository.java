package com.generation.vsnbackend.model.repositories;

import com.generation.vsnbackend.model.entities.images.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Long>
{
	Optional<FileData> findByName(String fileName);
}
