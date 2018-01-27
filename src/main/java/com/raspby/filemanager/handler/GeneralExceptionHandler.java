/**
 * 
 */
package com.raspby.filemanager.handler;

import java.nio.file.FileAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.raspby.filemanager.exceptions.GeneralException;

/**
 * @author Manuel
 *
 */
@ControllerAdvice
public class GeneralExceptionHandler {

	@ExceptionHandler(GeneralException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GeneralException fileAlreadyExistsException(GeneralException ex) {
		 return ex;
	}
}
