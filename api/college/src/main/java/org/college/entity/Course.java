/**
 * 
 */
package org.college.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@Entity
@Table(name = "course", schema = "public")
public class Course {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull(message = "Course Name can not be null")
	@Column(unique = true, name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description", length = 5000)
	private String description;

	public Course() {
	}

	public Course(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Course(Long id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
