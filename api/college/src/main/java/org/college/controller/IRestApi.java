package org.college.controller;

import java.util.List;

import org.college.entity.Course;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.ApiOperation;

public interface IRestApi {

	@ApiOperation(value = "View all courses")
	public ResponseEntity<List<Course>> getCourses();

	@ApiOperation(value = "Add a new Course")
	public ResponseEntity<Course> addCourse(Course course);

	@ApiOperation(value = "Update an existing Course")
	public ResponseEntity<Course> updateCourse(Course course);

	@ApiOperation(value = "Delete an existing Course")
	public ResponseEntity<Boolean> deleteCourse(Course course);
}
