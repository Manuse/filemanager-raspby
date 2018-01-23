/**
 * 
 */
package com.raspby.filemanager.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotBlank;

/**
 * @author Manuel
 *
 */
@Entity
@Table(name="access_path")
public class AccessPath extends IdEntity{

	@Column(name="device")
	@NotBlank
	private String device;
	
	@Column(name="path")
	@NotBlank
	private String path;
	
	@Column(name="permissions")
	private short permissions;
	
	@Column(name="space_used")
	private double spaceUsed;
	
	@Column(name="space_limit")
	private double spaceLimit;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user;

	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the permissions
	 */
	public short getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(short permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * @return the spaceUsed
	 */
	public double getSpaceUsed() {
		return spaceUsed;
	}

	/**
	 * @param spaceUsed the spaceUsed to set
	 */
	public void setSpaceUsed(double spaceUsed) {
		this.spaceUsed = spaceUsed;
	}

	/**
	 * @return the spaceLimit
	 */
	public double getSpaceLimit() {
		return spaceLimit;
	}

	/**
	 * @param spaceLimit the spaceLimit to set
	 */
	public void setSpaceLimit(double spaceLimit) {
		this.spaceLimit = spaceLimit;
	}

	
	
	
	
}
