/**
 * 
 */
package com.raspby.filemanager.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Manuel
 *
 */
@Entity
@Table(name="authority")
public class Authority extends IdEntity{

	@Column(name="name")
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}	
	
}
