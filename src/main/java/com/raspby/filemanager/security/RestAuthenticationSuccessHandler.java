package com.raspby.filemanager.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.raspby.filemanager.persistence.Users;
import com.raspby.filemanager.repository.UsersRepository;


/**
 * 
 * @author Manuel
 *
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		Users user = usersRepository.findByUsername(authentication.getName());
		SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, user);
	}
}
