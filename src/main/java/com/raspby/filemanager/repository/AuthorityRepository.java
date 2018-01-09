/**
 * 
 */
package com.raspby.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raspby.filemanager.persistence.Authority;

/**
 * @author Manuel
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority, Short>{

}
