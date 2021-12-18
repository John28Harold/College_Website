/**
 * 
 */
package org.college.repository;

import org.college.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vivek Sharma
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}
