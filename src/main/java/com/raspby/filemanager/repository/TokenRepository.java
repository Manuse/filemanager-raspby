/**
 * 
 */
package com.raspby.filemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raspby.filemanager.persistence.Token;

/**
 * @author Manuel
 *
 */
public interface TokenRepository extends JpaRepository<Token, String>{

}
