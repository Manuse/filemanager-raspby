package com.raspby.filemanager.service;

import java.util.List;

import com.raspby.filemanager.persistence.Users;

/**
 * 
 * @author Manuel
 *
 */
public interface UsersService {

	public Users findByUsername(String username);
	
	public boolean deleteUsers(short id);
	
	public Users saveUser(Users users);
	
	public Users findOne(short id);
	
	public List<Users> findAll();
	
	public Users updatePass(short id, String newPass);
	
	public Users updatePass(String newPass);
	
	public Users changeEnable(short id);
}
