package com.raspby.filemanager.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raspby.filemanager.model.CustomFile;
import com.raspby.filemanager.service.FileService;

/**
 * 
 * @author Manuel
 *
 */
@RestController
public class FileController {

	long size = 0l;

	// private Map inputs = new

	@Autowired
	private FileService fileService;

	@GetMapping("/file/roots")
	public List<CustomFile> getRoots(@RequestParam("userId") short userId) {
		return fileService.getRoots(userId);
	}

	@GetMapping("/file/files")
	public FileResponse getFiles(@RequestParam("userId") short userId, @RequestParam("device") String device,
			@RequestParam("path") String path) {

		FileResponse fileResponse = new FileResponse();
		fileResponse.setFiles(fileService.getFiles(userId, device, path));
		fileResponse.setPermission(fileService.getPermission(userId, device, path));
		return fileResponse;
	}

	@GetMapping("file/download")
	public ResponseEntity<Resource> download(HttpServletRequest request, @RequestParam("userId") short userId,
			@RequestParam("device") String device, @RequestParam("path") String path) throws IOException {

		ServletContext context = request.getServletContext();
		// path = "/"+path.replace("-", "/")+"/"+fileName;
		System.err.println(userId + " " + device + " " + path);
		File file = fileService.downloadFile(userId, device, path);
		String mimeType = context.getMimeType(file.getAbsolutePath());
		System.err.println(mimeType);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + file.getName());
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		FileInputStream fis = new FileInputStream(file);
		headers.setContentLength(file.length());
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType(mimeType)).body(new InputStreamResource(fis));
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "Content-type=multipart/form-data")
	public CustomFile uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam("path") String path,
			@RequestParam("fileSize") long fileSize, @RequestParam("userId") short userId,
			@RequestParam("device") String device) throws IOException {
		
		System.err.println(path);
		System.err.println(fileSize);
		// System.err.println(file.getOriginalFilename());
		// System.err.println(file.getSize());
		System.err.println(size);
		System.err.println(path + "/" + file.getOriginalFilename() + ".upload");
		return fileService.uploadFile(userId, device, path, fileSize, file);
	}
	
	@DeleteMapping("/file/delete")
	public boolean deleteFile(@RequestParam("userId") short userId, @RequestParam("device") String device,
			@RequestParam("path") String path) throws FileNotFoundException {
		return fileService.deleteFile(userId, device, path);
	}

	@PostMapping("/file/mkdir")
	public CustomFile mkDir(@RequestParam("userId") short userId, @RequestParam("device") String device,
			@RequestParam("path") String path, @RequestParam("newDir") String newDir)
			throws FileAlreadyExistsException {
		
		return fileService.mkDir(userId, device, path, newDir);
	}

	@GetMapping("/upload/size")
	public @ResponseBody Long uploadFileSize(@RequestParam("path") String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.length();
		}
		return new File(path + ".upload").length();
	}

	static class FileResponse {

		List<CustomFile> files;

		Short permission;

		/**
		 * @return the customFiles
		 */
		public List<CustomFile> getFiles() {
			return files;
		}

		/**
		 * @param customFiles
		 *            the customFiles to set
		 */
		public void setFiles(List<CustomFile> files) {
			this.files = files;
		}

		/**
		 * @return the permission
		 */
		public Short getPermission() {
			return permission;
		}

		/**
		 * @param permission
		 *            the permission to set
		 */
		public void setPermission(Short permission) {
			this.permission = permission;
		}

	}
}
