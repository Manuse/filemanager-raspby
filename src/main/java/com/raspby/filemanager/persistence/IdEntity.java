/**
 * 
 */
package com.raspby.filemanager.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Manuel
 *
 */
@MappedSuperclass
public class IdEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Short id;

	/**
	 * @return the id
	 */
	public Short getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Short id) {
		this.id = id;
	}
	
	
}
