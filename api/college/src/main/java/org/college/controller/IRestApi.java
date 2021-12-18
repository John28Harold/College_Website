package org.college.controller;

import java.util.List;

import org.college.entity.Course;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

public interface IRestApi {

	@ApiOperation(value = "View all courses", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<List<Course>> getCourses();

	@ApiOperation(value = "Add a new Course", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<Course> addCourse(Course course);

	@ApiOperation(value = "Update an existing Course", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<Course> updateCourse(Course course);

	@ApiOperation(value = "Delete an existing Course", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<Boolean> deleteCourse(Course course);
}
