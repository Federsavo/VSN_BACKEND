package com.generation.vsnbackend.controller.images;

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

	private final static String FOLDER_PATH = System.getProperty("user.dir")+"\\Desktop\\test\\";

	public String uploadImageToFileSystem(MultipartFile file) throws IOException
	{
		String filePath=FOLDER_PATH+file.getOriginalFilename();

		FileData fileData=fileDataRepository.save(new FileData(file.getOriginalFilename(),file.getContentType(),filePath));

		// Creiamo l'oggetto File
		File folder = new File(filePath);

		// Verifichiamo che non sia già esistente come cartella
		if(!folder.isDirectory()){

			// In caso non sia già presente, la creiamo
			folder.mkdir();

		}


		file.transferTo(folder);

		if (fileData != null) {
			return "file uploaded successfully : " + filePath;
		}
		return null;
	}

	public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
		Optional<FileData> fileData = fileDataRepository.findByName(fileName);
		if(fileData.isPresent())
		{
			String filePath = fileData.get().getFilePath();
			byte[] image = Files.readAllBytes(new File(filePath).toPath());
			return image;
		}
		return null;
	}

}
