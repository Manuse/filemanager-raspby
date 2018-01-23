/**
 * 
 */
package com.raspby.filemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raspby.filemanager.persistence.Users;
import com.raspby.filemanager.repository.UsersRepository;

/**
 * @author Manuel
 *
 */
@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	@Transactional(readOnly=true)
	public Users findByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public boolean deleteUsers(short id) {
		usersRepository.deleteById(id);
		return !usersRepository.existsById(id);
	}

	@Override
	@Transactional
	public Users saveUser(Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		return usersRepository.save(users);
	}

	@Override
	@Transactional(readOnly=true)
	public Users findOne(short id) {
		return usersRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Users> findAll() {
		return usersRepository.findAll();
	}

	@Override
	@Transactional
	public Users updatePass(short id, String oldPass, String newPass) {
		Users user = usersRepository.findByIdAndPassword(id, passwordEncoder.encode(oldPass));
		if(user!=null) {
			user.setPassword(passwordEncoder.encode(newPass));
			return usersRepository.save(user);
		}
		return null;
	}

	@Override
	@Transactional
	public Users changeEnable(short id) {
		Users user = usersRepository.findById(id).get();
		System.err.println(!user.isEnabled());
		user.setEnabled(!user.isEnabled());
		return usersRepository.save(user);
	}

	
}
