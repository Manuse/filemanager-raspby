/**
 * 
 */
package com.raspby.filemanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Manuel
 *
 */
@JsonInclude(value=Include.NON_NULL)
public class CustomFile {

	private String name;
	
	private String path;
	
	private String parentPath;
	
	private Long lastModified;
	
	private Double size;
	
	private boolean directory;
	
	private String Device;
	
	private Boolean rootFile;
	
	public CustomFile() {}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}



	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	

	/**
	 * @return the lastModified
	 */
	public Long getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the parentPath
	 */
	public String getParentPath() {
		return parentPath;
	}

	/**
	 * @param parentPath the parentPath to set
	 */
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	/**
	 * @return the directory
	 */
	public boolean isDirectory() {
		return directory;
	}

	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Double size) {
		this.size = size;
	}

	/**
	 * @return the size
	 */
	public Double getSize() {
		return size;
	}


	/**
	 * @return the device
	 */
	public String getDevice() {
		return Device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		Device = device;
	}

	/**
	 * @return the isRootFile
	 */
	public Boolean isRootFile() {
		return rootFile;
	}

	/**
	 * @param isRootFile the isRootFile to set
	 */
	public void setRootFile(Boolean isRootFile) {
		this.rootFile = isRootFile;
	}
	
	
	
}
