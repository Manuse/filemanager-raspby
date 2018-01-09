/**
 * 
 */
package com.raspby.filemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raspby.filemanager.persistence.Users;
import com.raspby.filemanager.service.UsersService;

/**
 * @author Manuel
 *
 */
@RestController
@CrossOrigin
public class UsersController {

	@Autowired
	private UsersService usersService;
	

	@GetMapping("/admin/users")
	public List<Users> getUsers(){
		return usersService.findAll();
	}
	
	@PostMapping("/admin/user")
	public Users saveUser(@RequestBody Users users) {
		return usersService.saveUser(users);
	}
	
	@DeleteMapping("/admin/user")
	public boolean deleteUser(@RequestParam("id")short id) {
		return usersService.deleteUsers(id);
	}
	
	@PutMapping("/user/update-pass")
	public Users updatePass(@RequestParam("id")short id, @RequestParam("oldPass")String oldPass, @RequestParam("newPass")String newPass) {
		return usersService.updatePass(id, oldPass, newPass);
	}
}
