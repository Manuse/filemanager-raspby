/**
 * 
 */
package com.raspby.filemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.raspby.filemanager.persistence.AccessPath;

/**
 * @author Manuel
 *
 */
public interface AccessPathRepository extends JpaRepository<AccessPath, Short>{

	/**
	 * get distinct device by user id
	 * @param id - user id
	 * @return list with device
	 */
	@Query("select distinct a.device from AccessPath a where a.user.id=:id")
	public List<String> findDistinctDeviceByUserId(@Param("id")short id);
	
	/**
	 * get AccessPath by user id and device
	 * @param id - user id
	 * @param device - device where are the files 
	 * @return list of AccessPath
	 */
	public List<AccessPath> findByUserIdAndDevice(short id, String device);
	
	/**
	 * get AccessPath by id user, device and parameter path include path of object
	 * @param id - user id
	 * @param device - device where are the files
	 * @param path - path of file in device
	 * @return list of AccessPath 
	 */
	@Query("from AccessPath a where a.user.id=:id and (a.device like :device or a.device='*') and :path like concat(a.path,'%')")
	public List<AccessPath> findByUserIdAndDeviceAndLikePathParam(@Param("id") short id, @Param("device") String device, @Param("path")String path);
	
	/**
	 * get permissions in one path
	 * @param id - user id
	 * @param device - device where are the files
	 * @param path - path of file in device
	 * @return 0 if is write or 1 if is read
	 */
	@Query("select a.permissions from AccessPath a where a.user.id=:id and (a.device like :device or a.device='*') and :path like concat(a.path,'%')")
	public Short findPermissionsByUserIdAndDeviceAndLikePathParam(@Param("id") short id, @Param("device") String device, @Param("path")String path);
	
	/**
	 * get list with AccessPath where path is parent or child
	 * @param userId - id of user 
	 * @param device - device
	 * @param path - path
	 * @return list with AccessPath
	 */
	@Query("from AccessPath a where a.user.id=:id and (a.device like :device or a.device='*') and (:path like concat(a.path,'%') or a.path like concat(:path,'%'))")
	public List<AccessPath> findByUserIdAndDeviceAndLikePathOrLikePathParam(@Param("id") short userId, @Param("device") String device, @Param("path")String path);

	/**
	 * get list of AccessPath by user id 
	 * @param userId - id of user
	 * @return list with AccessPath
	 */
	public List<AccessPath> findByUserId(short userId);
}
