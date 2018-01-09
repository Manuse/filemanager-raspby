package com.raspby.filemanager.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotEnabledException extends AuthenticationException {


	private static final long serialVersionUID = 9204227726573244359L;

	public UserNotEnabledException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotEnabledException(String msg) {
        super(msg);
    }
}
