package com.raspby.filemanager.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.raspby.filemanager.exceptions.UnauthorizedException;
import com.raspby.filemanager.model.CustomFile;
import com.raspby.filemanager.persistence.AccessPath;
import com.raspby.filemanager.repository.AccessPathRepository;
import com.raspby.filemanager.util.MegabytesConverter;

/**
 * 
 * @author Manuel
 *
 */
@Service
public class FileServiceImpl implements FileService {

	// private static Logger h;

	public static final String DEFAULT_PATH = "ejemplo";
	public static final String DEFAULT_PATH_WITH_BAR = DEFAULT_PATH + "/";
	public static final String ALL_DEVICES = "*";
	public static final short READ = 1;
	public static final short WRITE = 0;

	// public static final String DEFAULT_PATH="/media";

	@Autowired
	private AccessPathRepository accessPathRepository;

	@Override
	@Transactional
	public List<CustomFile> getRootFiles(short userId, String device) {
		List<CustomFile> customFiles = new ArrayList<CustomFile>();
		File file = null;
		if (!accessPathRepository.findDistinctDeviceByUserId(userId).contains(ALL_DEVICES)) {
			List<AccessPath> accessPath = accessPathRepository.findByUserIdAndDevice(userId, device);
			for (AccessPath ap : accessPath) {
				file = new File(DEFAULT_PATH_WITH_BAR + device + ap.getPath());
				if (!file.exists()) {
					file.mkdirs();
				}
				customFiles.add(makeCustomFile(file, true));
			}
		} else {
			file = new File(DEFAULT_PATH_WITH_BAR + device);
			for (File f : file.listFiles()) {
				customFiles.add(makeCustomFile(f, true));
			}
		}
		return customFiles;
	}

	@Override
	@Transactional
	public File downloadFile(short userId, String device, String path) throws FileNotFoundException {
		File file = null;
		if (authorized(userId, device, path)) {
			file = new File(DEFAULT_PATH_WITH_BAR + device + path);
			if (file.exists() && !file.isDirectory()) {
				return file;
			} else {
				throw new FileNotFoundException("archivo no encontrado");
			}
		} else {
			throw new UnauthorizedException("no autorizado");
		}
	}

	@Override
	@Transactional
	public List<CustomFile> getRoots(short userId) {
		List<String> userDevice = accessPathRepository.findDistinctDeviceByUserId(userId);// .stream()
		// .map(AccessPath::getDevice).collect(Collectors.toList());//
		List<CustomFile> cf = new ArrayList<CustomFile>();	
		List<String> devices = getCurrentDevices();
		if (userDevice.contains(ALL_DEVICES)) {
			for (String st : devices) {
				cf.add(makeCustomFile(new File(DEFAULT_PATH_WITH_BAR + st), null));
			}
		} else {
			for (String device : userDevice) {
				if (devices.contains(device)) {
					cf.add(makeCustomFile(new File(DEFAULT_PATH_WITH_BAR + device), null));
				}
			}
		}
		return cf;
	}

	@Override
	public List<CustomFile> getFiles(short userId, String device, String path) {
		List<CustomFile> list = new ArrayList<CustomFile>();

		// si no esta el dispositivo conectado vuelve a roots
		if (!getRoots(userId).stream().map(CustomFile::getDevice).collect(Collectors.toList()).contains(device)) {
			return getRoots(userId);
		}

		if (authorized(userId, device, path)) {// si esta autorizado carga los archivos normal
			System.err.println("file normal");
			File file = new File(DEFAULT_PATH_WITH_BAR + device + path);
			for (File f : file.listFiles()) {
				list.add(makeCustomFile(f, path.equals("/")));
			}
		} else {// si no esta autorizado carga los archivos del root actual
			System.err.println("root files");
			return getRootFiles(userId, device);
		}
		return list;
	}

	private CustomFile makeCustomFile(File file, Boolean isRootFile) {
		CustomFile cf = new CustomFile();
		// linux
		// String device = file.getAbsolutePath().replace("\\", "/").split("/")[1];
		// String path = file.getAbsolutePath().replace("\\",
		// "/").replace(DEFAULT_PATH_WITH_BAR, "").replace(device, "");
		// windows src
		String device = file.getPath().replace("\\", "/").split("/")[1];
		String path = file.getPath().replace("\\", "/").replace(DEFAULT_PATH_WITH_BAR + device, "");
		String parent = "";
		if (!path.isEmpty()) {
			parent = file.getParent().replace("\\", "/").replace(DEFAULT_PATH_WITH_BAR + device, "");
		}

		cf.setLastModified(file.lastModified());
		cf.setName(file.getName());
		cf.setDirectory(file.isDirectory());
		cf.setPath(path);
		cf.setRootFile(isRootFile);
		cf.setParentPath(parent);
		cf.setDevice(device);
		if (!cf.isDirectory()) {
			cf.setSize(MegabytesConverter.converterBytesToMegabytes(file.length()));
		}
		return cf;
	}

	@Override
	@Transactional
	public CustomFile mkDir(short userId, String device, String path, String newDir) throws FileAlreadyExistsException {
		if (authorized(userId, device, path)) {
			File file = new File(DEFAULT_PATH_WITH_BAR + device + path + "/" + newDir);
			if (!file.exists()) {
				file.mkdirs();
				return makeCustomFile(file, path.equals("/"));
			} else {
				throw new FileAlreadyExistsException("ya existe la carpeta");
			}
		} else {
			throw new UnauthorizedException("no autorizado");
		}

	}

	/**
	 * verify if user is authorized
	 * 
	 * @param userId
	 * @param device
	 * @param path
	 * @return true if is authorized else false
	 */
	private boolean authorized(short userId, String device, String path) {
		return !accessPathRepository.findByUserIdAndDeviceAndLikePathParam(userId, device, path).isEmpty();
	}

	@Override
	@Transactional
	public CustomFile uploadFile(short userId, String device, String path, long fileSize, MultipartFile file)
			throws IOException {
		if (authorized(userId, device, path)) {
			File f = new File(DEFAULT_PATH_WITH_BAR + device + path + "/" + file.getOriginalFilename() + ".upload");
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(
					DEFAULT_PATH_WITH_BAR + device + path + "/" + file.getOriginalFilename() + ".upload", true);
			fos.write(file.getBytes());
			fos.flush();
			fos.close();
			f = new File(DEFAULT_PATH_WITH_BAR + device + path + "/" + file.getOriginalFilename() + ".upload");
			System.err.println(f.length());
			if (f.length() == fileSize) {
				File renameFile = new File(DEFAULT_PATH_WITH_BAR + device + path + "/" + file.getOriginalFilename());
				if (renameFile.exists()) {
					renameFile.delete();
				}
				f.renameTo(renameFile);
				return makeCustomFile(renameFile, null);
			}
		} else {
			throw new UnauthorizedException("no autorizado");
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public short getPermission(short userId, String device, String path) {
		Short perm = accessPathRepository.findPermissionsByUserIdAndDeviceAndLikePathParam(userId, device, path);
		return perm == null ? READ : perm;
	}

	@Override
	@Transactional
	public boolean deleteFile(short userId, String device, String path) throws FileNotFoundException {
		if (authorized(userId, device, path)) {
			File file = new File(DEFAULT_PATH_WITH_BAR + device + path);
			if (file.exists()) {
				return deleteDir(file);
			} else {
				throw new FileNotFoundException("archivo no encontrado");
			}
		} else {
			throw new UnauthorizedException("no autorizado");
		}

	}

	private boolean deleteDir(File file) {
		if (file.delete()) {
			return true;
		}
		for (File f : file.listFiles()) {
			deleteDir(f);
		}
		return file.delete();
	}

	@Override
	@Transactional(readOnly=true)
	public List<String> getCurrentDevices() {
		File file = new File(DEFAULT_PATH_WITH_BAR);
		return Arrays.asList(file.list());
	}

}
