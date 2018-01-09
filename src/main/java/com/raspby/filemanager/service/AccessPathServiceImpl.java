package com.raspby.filemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raspby.filemanager.persistence.AccessPath;
import com.raspby.filemanager.repository.AccessPathRepository;

/**
 * 
 * @author Manuel
 *
 */
@Service
public class AccessPathServiceImpl implements AccessPathService{

	@Autowired
	private AccessPathRepository accessPathRepository;

	@Override
	@Transactional
	public boolean checkPath(short userId, String device, String path) {		
		return !accessPathRepository.findByUserIdAndDeviceAndLikePathOrLikePathParam(userId, device, path).isEmpty();
	}

	@Override
	@Transactional
	public AccessPath addAccessPath(AccessPath accessPath) {
		if(checkPath(accessPath.getUser().getId(), accessPath.getDevice(), accessPath.getPath())) {
			accessPathRepository.delete(accessPathRepository.findByUserIdAndDeviceAndLikePathOrLikePathParam(accessPath.getUser().getId(), accessPath.getDevice(), accessPath.getPath()));
		}
		return accessPathRepository.save(accessPath);
	}

	@Override
	@Transactional
	public boolean deleteAccessPath(short accessPathId) {
		accessPathRepository.delete(accessPathId);
		return !accessPathRepository.exists(accessPathId);
	}

	@Override
	public List<AccessPath> findByUserId(short userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
