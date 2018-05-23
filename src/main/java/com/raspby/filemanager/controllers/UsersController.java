/**
 * 
 */
package com.raspby.filemanager.controllers;

import java.util.ArrayList;
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

import com.raspby.filemanager.persistence.AccessPath;
import com.raspby.filemanager.persistence.Authority;
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
	public Users saveUser(@RequestBody UsersRequest usersRequest) {
		Users users=new Users();
		users.setAccessPath(new ArrayList<AccessPath>());
		users.setAuthorities(usersRequest.getAuthorities());
		users.setPassword(usersRequest.getPassword());
		users.setEnabled(usersRequest.isEnabled());
		users.setUsername(usersRequest.getUsername());
		return usersService.saveUser(users);
	}
	
	@DeleteMapping("/admin/user")
	public boolean deleteUser(@RequestParam("id")short id) {
		return usersService.deleteUsers(id);
	}
	
	@PutMapping("/user/update-pass")
	public Users updatePass(@RequestParam("newPass")String newPass) {
		return usersService.updatePass(newPass);
	}
	
	@PutMapping("/admin/user/update-pass")
	public Users updatePass(@RequestParam("id")short id, @RequestParam("newPass")String newPass) {
		return usersService.updatePass(id, newPass);
	}
	
	@PutMapping("/admin/change-enabled")
	public Users changeEnabled(@RequestParam("id")short id) {
		return usersService.changeEnable(id);
	}
	
	static class UsersRequest{
		
//		username: vm.newUsername,
//        password: vm.newPassword,
//        enabled: true,
//        authorities: auth
		String username;
		
		String password;
		
		boolean enabled;
		
		List<Authority> authorities;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public List<Authority> getAuthorities() {
			return authorities;
		}

		public void setAuthorities(List<Authority> authorities) {
			this.authorities = authorities;
		}
		
	}
}
