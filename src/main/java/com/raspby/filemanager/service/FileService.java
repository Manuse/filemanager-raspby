/**
 * 
 */
package com.raspby.filemanager.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raspby.filemanager.model.CustomFile;




/**
 * @author Manuel
 *
 */
public interface FileService {

	public List<CustomFile> getFiles(short userId, String device, String path);
	
	public List<CustomFile> getRootFiles(short userId, String device);
	
	public File downloadFile(short userId, String path, String device);
	
	public List<CustomFile> getRoots(short userId);
	
	public CustomFile mkDir(short userId, String device, String path, String newDir);
	
	public CustomFile uploadFile(short userId, String device, String path, long fileSize,MultipartFile file);
	
	public short getPermission(short userId, String device, String path);
	
	public boolean deleteFile(short userId, String device, String path);
	
	public List<String> getCurrentDevices();
	
	public List<String> searchPath(String device, String partialPath);
	
}
