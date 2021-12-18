/**
 * 
 */
package org.college.repository;

import org.college.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vivek Sharma
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByName(String name);
}
