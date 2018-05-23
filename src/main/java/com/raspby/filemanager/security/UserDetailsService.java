package com.raspby.filemanager.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.raspby.filemanager.exceptions.UserNotEnabledException;
import com.raspby.filemanager.persistence.Authority;
import com.raspby.filemanager.persistence.Users;
import com.raspby.filemanager.repository.UsersRepository;

/**
 * 
 * @author Manuel
 *
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
    private UsersRepository usersRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        } else if (!user.isEnabled()) {
            throw new UserNotEnabledException("User " + username + " was not enabled");
        }

        Collection<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
																		.filter(u -> u.getName().contains("filemanager"))
																		.map(u -> new SimpleGrantedAuthority(u.getName()))
																		.collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                grantedAuthorities);
	}

}
