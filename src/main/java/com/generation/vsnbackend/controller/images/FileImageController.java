package com.generation.vsnbackend.controller.images;

import com.generation.vsnbackend.model.entities.images.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class FileImageController
{
	@Autowired
	private FileDataService service;

	@PostMapping("/fileSystem")
	public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file) throws IOException
	{
		FileData uploadImage = service.uploadImageToFileSystem(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}

	@GetMapping("/fileSystem/{id}")
	public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable Long id) throws IOException {
		byte[] imageData=service.downloadImageFromFileSystem(id);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.parseMediaType("image/png"))
				.body(imageData);

	}
}
