/**
 * 
 */
package com.raspby.filemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.raspby.filemanager.persistence.Token;
import com.raspby.filemanager.persistence.Users;
import com.raspby.filemanager.repository.TokenRepository;
import com.raspby.filemanager.security.SecurityUtils;
import com.raspby.filemanager.service.UsersService;

/**
 * @author Manuel
 *
 */
@RestController
@CrossOrigin
public class SecurityController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private TokenRepository tokenRepository;

	@GetMapping("/security/account")
	public Users getUserAccount() {
		Users user = usersService.findByUsername(SecurityUtils.getCurrentLogin());
		user.setPassword(null);
		return user;
	}

	@PreAuthorize("hasAuthority('filemanager-super-admin')")
	@GetMapping("/security/tokens")
	public @ResponseBody List<Token> getTokens() {
		List<Token> tokens = tokenRepository.findAll();
		for (Token t : tokens) {
			t.setSeries(null);
			t.setValue(null);
		}
		return tokens;
	}

}
