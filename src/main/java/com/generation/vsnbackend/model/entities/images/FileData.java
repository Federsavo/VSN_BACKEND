package com.generation.vsnbackend.model.entities.images;

import com.generation.vsnbackend.model.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents a file entity stored in the system.
 * The FileData class is used to hold information about a file,
 * including its name, type, and file path. This class extends the
 * BaseEntity class, which typically contains common fields
 * such as an ID and timestamps for creation and modification.
 */
@Entity
@Table(name="FILE_DATA")
public class FileData extends BaseEntity
{
	private String name;
	private String type;
	private String filePath;

	public FileData(){}

	public FileData(String name, String type, String filePath)
	{
		this.name = name;
		this.type = type;
		this.filePath = filePath;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
}
