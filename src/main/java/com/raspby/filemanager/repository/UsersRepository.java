/**
 * 
 */
package com.raspby.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raspby.filemanager.persistence.Users;

/**
 * @author Manuel
 *
 */
public interface UsersRepository extends JpaRepository<Users, Short>{
	
	public Users findByUsername(String username);
	
	public Users findByUsernameAndPassword(String username, String password);
	
	public Users findByIdAndPassword(Short id, String password);

}
