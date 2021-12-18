package org.college.controller;

import java.util.List;

import javax.validation.Valid;

import org.college.entity.Course;
import org.college.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nitk/courses")
public class ApiController implements IRestApi {

	@Autowired
	private CourseService courseService;

	public ApiController() {
	}

	@Override
	@GetMapping
	public ResponseEntity<List<Course>> getCourses() {
		return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
	}

	@Override
	@PostMapping
	public ResponseEntity<Course> addCourse(@RequestBody @Valid Course course) {
		return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
	}

	@Override
	@PutMapping
	public ResponseEntity<Course> updateCourse(@RequestBody @Valid Course course) {
		return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.OK);
	}

	@Override
	@DeleteMapping
	public ResponseEntity<Boolean> deleteCourse(@RequestBody Course course) {
		return new ResponseEntity<>(courseService.deleteCourse(course), HttpStatus.OK);
	}

}
