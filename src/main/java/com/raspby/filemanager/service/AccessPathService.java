/**
 * 
 */
package com.raspby.filemanager.service;

import java.util.List;

import com.raspby.filemanager.persistence.AccessPath;

/**
 * @author Manuel
 *
 */
public interface AccessPathService {

	/**
	 * Add access path to user, if is parent replace children access path else is child replace the parent
	 * @param accessPath new AccessPath
	 * @return new AccessPath
	 */
	public AccessPath addAccessPath(AccessPath accessPath);
	
	/**
	 * Delete an access path to a user
	 * @param accessPathId id of access path
	 * @return true if remove otherwise false
	 */
	public boolean deleteAccessPath(short accessPathId);
	
	/**
	 * Check if the path is child or parent
	 * @param userId - user id
	 * @param device - device 
	 * @param path - path
	 * @return true if is a child or parent else false
	 */
	public boolean checkPath(short userId, String device, String path);
	
	/**
	 * get Access path of a user
	 * @param userId - id of user
	 * @return list with AccessPath 
	 */
	public List<AccessPath> findByUserId(short userId);
	
	/**
	 * change permission of one Access Path
	 * @param accessPathId access path id
	 * @param permission new permission
	 * @return AccessPath modified
	 */
	public AccessPath changePermission(short accessPathId, short permission);
}
