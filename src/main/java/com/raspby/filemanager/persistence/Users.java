/**
 * 
 */
package com.raspby.filemanager.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Manuel
 *
 */
@Entity
@Table(name="users")
public class Users extends IdEntity{

	@Column(name="login")
	@NotBlank
	private String username;
	
	@Column(name="password")
	@NotBlank
	@JsonIgnore
	private String password;
	
	@Column(name="enabled")
	@NotNull
	private boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_authority", joinColumns = { @JoinColumn(name = "id_user", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "id_authority", table = "authority", referencedColumnName = "id") })
	private List<Authority> authorities = new ArrayList<Authority>();
	
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<AccessPath> accessPath;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the authorities
	 */
	public List<Authority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}


	/**
	 * @return the accessPath
	 */
	public List<AccessPath> getAccessPath() {
		return accessPath;
	}

	/**
	 * @param accessPath the accessPath to set
	 */
	public void setAccessPath(List<AccessPath> accessPath) {
		this.accessPath = accessPath;
	}
	
	
	
}
