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

	/**
	 * Uploads an image file to the file system and saves its metadata to the database.
	 *
	 * This method first checks if the specified directory exists and creates it if not.
	 * It constructs a file name using the provided identifier and the original file name,
	 * and checks the database for any existing entries with the same name. If found,
	 * the existing entry is deleted. The file is then saved to the specified location,
	 * and its details are stored in the database.
	 *
	 *
	 * @param file The MultipartFile representing the image file to be uploaded.
	 * @param id The identifier used to create a unique file name for the uploaded image.
	 * @return The saved FileData object containing the metadata of the uploaded file.
	 * @throws IOException If an error occurs while uploading the file or accessing the file system.
	 */
	public FileData uploadImageToFileSystem(MultipartFile file, Long id) throws IOException
	{
		createDirectoryIfNotExists(FOLDER_PATH);
		String fileName = id+"-"+file.getOriginalFilename();
		String filePath=FOLDER_PATH+fileName;
		Optional<FileData> fileDataToDelete=fileDataRepository.findByName(fileName);

		if(fileDataToDelete.isPresent())
			fileDataRepository.delete(fileDataToDelete.get());

		FileData fileData=fileDataRepository.save(new FileData(fileName,file.getContentType(),filePath));
		File folder = new File(filePath);
		file.transferTo(folder);

		return fileData;
	}

	/**
	 * Creates a directory at the specified path if it does not already exist.
	 *
	 * This method checks whether the directory specified by the given path
	 * exists. If it does not exist, it attempts to create the directory and
	 * any necessary parent directories. If the directory already exists,
	 * no action is taken.
	 *
	 * @param path The file system path where the directory should be created.
	 *             This path can include parent directories, which will also
	 *             be created if they do not exist.
	 */
	public void createDirectoryIfNotExists(String path) {
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}


	/**
	 * Downloads an image from the file system as a byte array using the specified identifier.
	 *
	 * This method retrieves the image file data associated with the given ID from the
	 * {@link FileData} repository. If the file data is found, it reads the image file from
	 * the file system and returns its contents as a byte array. If the image cannot be found,
	 * an {@link ImageNotFoundException} is thrown.
	 *
	 * @param id The unique identifier of the image to be downloaded.
	 * @return A byte array containing the image data.
	 * @throws IOException If an I/O error occurs while reading the image file.
	 * @throws ImageNotFoundException If no image is found associated with the given ID.
	 */
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
