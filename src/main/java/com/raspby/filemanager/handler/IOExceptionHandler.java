/**
 * 
 */
package com.raspby.filemanager.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Manuel
 *
 */
@ControllerAdvice
public class IOExceptionHandler {

	
	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public FileNotFoundException fileNotFoundException(FileNotFoundException ex) {
//		 Map<String, Object> map = new HashMap<String, Object>();
//		 map-
		 return ex;
	}
	
	@ExceptionHandler(FileAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public FileAlreadyExistsException fileAlreadyExistsException(FileAlreadyExistsException ex) {
//		 Map<String, Object> map = new HashMap<String, Object>();
//		 map-
		 return ex;
	}
	
}
