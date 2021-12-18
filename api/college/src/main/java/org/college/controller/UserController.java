/**
 * 
 */
package org.college.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.college.constants.AppConstants;
import org.college.entity.Role;
import org.college.entity.User;
import org.college.exception.ServiceException;
import org.college.model.JwtToken;
import org.college.model.ResultStatus;
import org.college.model.RoleToUser;
import org.college.service.UserService;
import org.college.utils.JWTUtils;
import org.college.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/nitk/admin")
public class UserController implements IUserApi {

	@Autowired
	private UserService userService;

	public UserController() {
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
	}

	@PostMapping("/user/save")
	public ResponseEntity<User> saveUsers(@RequestBody @Valid User user) {
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}

	@PostMapping("/role/save")
	public ResponseEntity<Role> saveRole(@RequestBody @Valid Role user) {
		return new ResponseEntity<>(userService.saveRole(user), HttpStatus.CREATED);
	}

	@PostMapping("/role/addtouser")
	public ResponseEntity<?> addRoleToUser(@RequestBody @Valid RoleToUser roleToUser) {
		userService.addRoleToUser(roleToUser.getUsername(), roleToUser.getRoleName());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (Utils.isNotBlankStr(token) && token.startsWith(AppConstants.BEARER)) {
			try {

				String refreshToken = token.substring(AppConstants.BEARER.length());
				String username = JWTUtils.getUsername(refreshToken);

				User user = userService.getUser(username);
				if (user == null)
					throw new UsernameNotFoundException(String.format("User %s does not exists", username));

				String url = request.getRequestURI().toString();

				String accessToken = JWTUtils.getAccessToken(user, url);
				JwtToken tokens = new JwtToken(accessToken, refreshToken);

				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);

			} catch (Exception e) {
				response.setStatus(HttpStatus.FORBIDDEN.value());

				ResultStatus resultStatus = new ResultStatus(AppConstants.FAILED, e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), resultStatus);
			}
		} else {
			throw new ServiceException("Refresh Token is Missing");
		}
	}

}
