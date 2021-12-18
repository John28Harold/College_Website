package org.college;

import org.college.constants.RoleEnum;
import org.college.entity.Role;
import org.college.entity.User;
import org.college.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollegeApplication implements CommandLineRunner {

	@Autowired
	private UserService service;

	public static void main(String[] args) {
		SpringApplication.run(CollegeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.saveRole(new Role(RoleEnum.ROLE_STUDENT.name()));
		service.saveRole(new Role(RoleEnum.ROLE_PROFESSOR.name()));
		service.saveRole(new Role(RoleEnum.ROLE_ADMIN.name()));

		service.saveUser(new User("vivek", "vivek@vivek.com", "vivek"));
		service.saveUser(new User("rohit", "rohit@rohit.com", "rohit"));
		service.saveUser(new User("charu", "charu@charu.com", "charu"));
		service.saveUser(new User("neeraj", "neeraj@neeraj.com", "neeraj"));

		service.addRoleToUser("charu@charu.com", RoleEnum.ROLE_ADMIN.name());
		service.addRoleToUser("neeraj@neeraj.com", RoleEnum.ROLE_ADMIN.name());
		service.addRoleToUser("vivek@vivek.com", RoleEnum.ROLE_PROFESSOR.name());
		service.addRoleToUser("rohit@rohit.com", RoleEnum.ROLE_STUDENT.name());
	}

}
