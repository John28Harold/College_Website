/**
 * 
 */
package org.college.config;

import org.college.constants.RoleEnum;
import org.college.filters.AuthenticationFilter;
import org.college.filters.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public SecurityConfig() {
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/nitk/admin/refresh-token/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/nitk/courses").hasAnyAuthority(
				RoleEnum.ROLE_STUDENT.name(), RoleEnum.ROLE_ADMIN.name(), RoleEnum.ROLE_PROFESSOR.name());
		http.authorizeRequests().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**",
				"/swagger.json").permitAll();
		http.authorizeRequests().antMatchers("/nitk/admin/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.name());
		http.authorizeRequests().antMatchers("/nitk/courses/**").hasAnyAuthority(RoleEnum.ROLE_ADMIN.name(),
				RoleEnum.ROLE_PROFESSOR.name());
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new AuthenticationFilter(authenticationManagerBean()));
		http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
