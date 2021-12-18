package org.college.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.college.entity.Role;
import org.college.entity.User;
import org.college.exception.NotFoundException;
import org.college.repository.RoleRepository;
import org.college.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserService() {
	}

	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	public void addRoleToUser(String username, String roleName) {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new NotFoundException(String.format("No User found with username %s ", username));

		Role role = roleRepository.findByName(roleName);
		if (role == null)
			throw new NotFoundException(String.format("No Role found with name %s ", roleName));

		user.getRoles().add(role);
		userRepository.save(user);
	}

	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> getUsers() {
		List<User> users = userRepository.findAll();
		if (users == null || users.isEmpty())
			return users;

		List<User> list = new ArrayList<>();
		users.forEach(user->{
			User u = new User();
			BeanUtils.copyProperties(user, u);
			u.setPassword(null);
			list.add(u);
		});

//		list.stream().map(e -> {
//			e.setPassword(null);
//			return e;
//		}).collect(Collectors.toList());

		return list;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found in database");
		}

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		user.getRoles().forEach(x -> authorities.add(new SimpleGrantedAuthority(x.getName())));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

}
