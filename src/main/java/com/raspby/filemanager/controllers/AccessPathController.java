package com.raspby.filemanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.raspby.filemanager.persistence.AccessPath;
import com.raspby.filemanager.service.AccessPathService;

/**
 * 
 * @author Manuel
 *
 */
@RestController
public class AccessPathController {

	
	private final AccessPathService accessPathService;
	
	
	
	/**
	 * @param accessPathService
	 */
	@Autowired
	public AccessPathController(AccessPathService accessPathService) {
		this.accessPathService = accessPathService;
	}



	public AccessPath addAccessPath(@RequestBody AccessPath accessPath) {
		return accessPathService.addAccessPath(accessPath);
	}
}
