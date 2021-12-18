/**
 * 
 */
package org.college.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.college.entity.Role;
import org.college.entity.User;
import org.college.model.RoleToUser;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

public interface IUserApi {

	@ApiOperation(value = "Get All Users", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<List<User>> getUsers();

	@ApiOperation(value = "Create User", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<User> saveUsers(User user);

	@ApiOperation(value = "Create Role", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<Role> saveRole(Role role);

	@ApiOperation(value = "Add role to user", authorizations = { @Authorization(value = "jwtToken") })
	public ResponseEntity<?> addRoleToUser(RoleToUser roleToUser);

	@ApiOperation(value = "Refresh Token", authorizations = { @Authorization(value = "jwtToken") })
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
