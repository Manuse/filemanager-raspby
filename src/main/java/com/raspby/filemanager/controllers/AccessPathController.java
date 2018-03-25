package com.raspby.filemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private AccessPathService accessPathService;


	@PostMapping("/admin/access-path")
	public AccessPath addAccessPath(@RequestBody AccessPath accessPath) {
		return accessPathService.addAccessPath(accessPath);
	}

	@DeleteMapping("/admin/access-path")
	public boolean deleteAccessPath(@RequestParam("accessPathId") short accessPathId) {
		return accessPathService.deleteAccessPath(accessPathId);
	}

	@GetMapping("/admin/check-path")
	public boolean checkPath(@RequestParam("userId") short userId, @RequestParam("device") String device,
			@RequestParam("path") String path) {
		return accessPathService.checkPath(userId, device, path);
	}

	@GetMapping("/admin/user-access-path")
	public List<AccessPath> findByUserId(@RequestParam("userId") short userId) {
		return accessPathService.findByUserId(userId);
	}

	@PutMapping("/admin/change-permission")
	public AccessPath changePermission(@RequestParam("accessPathId") short accessPathId,
			@RequestParam("permission") short permission) {
		return accessPathService.changePermission(accessPathId, permission);
	}
}
