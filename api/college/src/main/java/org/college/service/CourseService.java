package org.college.service;

import java.util.List;

import org.college.entity.Course;
import org.college.exception.DuplicateResourceException;
import org.college.exception.NotFoundException;
import org.college.exception.ServiceException;
import org.college.repository.CourseRepository;
import org.college.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

	public static final String NOT_EXIST = "Resourse that you are trying to access does not exist.";

	@Autowired
	private CourseRepository courseRepository;

	public CourseService() {
	}

	public List<Course> getCourses() {
		return courseRepository.findAll();
	}

	public Course saveCourse(Course course) {

		if (course.getId() == null)
			return save(course);
		else if (checkCourseExists(course.getId())) {
			Course entity = courseRepository.getOne(course.getId());
			convert(course, entity);
			return save(entity);
		} else {
			throw new NotFoundException(NOT_EXIST);
		}

	}

	private Course save(Course course) {
		try {
			return courseRepository.save(course);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				throw new DuplicateResourceException(e);
			}

			throw new ServiceException(e);
		}
	}

	public boolean deleteCourse(Course course) {
		if (course == null || course.getId() == null)
			throw new NotFoundException(NOT_EXIST);

		if (!checkCourseExists(course.getId()))
			throw new NotFoundException(NOT_EXIST);

		courseRepository.delete(course);

		return true;

	}

	private boolean checkCourseExists(Long id) {
		return courseRepository.existsById(id);
	}

	private void convert(Course source, Course destination) {
		if (Utils.isNotBlankStr(source.getDescription()))
			destination.setDescription(source.getDescription());

		if (Utils.isNotBlankStr(source.getName()))
			destination.setName(source.getName());
	}

}
