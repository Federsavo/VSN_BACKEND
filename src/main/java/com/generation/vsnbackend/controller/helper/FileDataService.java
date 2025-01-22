package com.generation.vsnbackend.controller.helper;

import com.generation.vsnbackend.controller.exception.ImageNotFoundException;
import com.generation.vsnbackend.model.entities.images.FileData;
import com.generation.vsnbackend.model.repositories.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileDataService
{
	@Autowired
	private FileDataRepository fileDataRepository;

	private final static String FOLDER_PATH = System.getProperty("user.dir")+"\\images\\";

	public FileData uploadImageToFileSystem(MultipartFile file) throws IOException
	{
		String filePath=FOLDER_PATH+file.getOriginalFilename();

		FileData fileData=fileDataRepository.save(new FileData(file.getOriginalFilename(),file.getContentType(),filePath));
		File folder = new File(filePath);
		file.transferTo(folder);

		return fileData;
	}

	public byte[] downloadImageFromFileSystem(Long id) throws IOException {
		Optional<FileData> fileData = fileDataRepository.findById(id);
		if(fileData.isPresent())
		{
			String filePath = fileData.get().getFilePath();
			byte[] image = Files.readAllBytes(new File(filePath).toPath());
			return image;
		}
		throw new ImageNotFoundException("Image not found");
	}

}
