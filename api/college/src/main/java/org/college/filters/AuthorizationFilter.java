/**
 * 
 */
package org.college.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.college.constants.AppConstants;
import org.college.model.ResultStatus;
import org.college.utils.JWTUtils;
import org.college.utils.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthorizationFilter extends OncePerRequestFilter {

	public AuthorizationFilter() {
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (skipValidation(request))
			filterChain.doFilter(request, response);
		else {
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (Utils.isNotBlankStr(token) && token.startsWith(AppConstants.BEARER)) {
				try {

					String accessToken = token.substring(AppConstants.BEARER.length());

					UsernamePasswordAuthenticationToken authenticationToken = JWTUtils.validateAccessToken(accessToken);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					response.setStatus(HttpStatus.FORBIDDEN.value());

					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					ResultStatus resultStatus = new ResultStatus(AppConstants.FAILED, e.getMessage());
					new ObjectMapper().writeValue(response.getOutputStream(), resultStatus);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}

	/**
	 * @param request
	 * @return
	 */
	private boolean skipValidation(HttpServletRequest request) {
		return request.getServletPath().equals(AppConstants.LOGIN)
				|| request.getServletPath().equals(AppConstants.REFRESH_TOKEN_URL);
	}

}
