/**
 * 
 */
package org.college.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.college.entity.Role;
import org.college.model.JwtToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtils {

	/**
	 * 
	 */
	private static final String ROLES = "roles";
	private static final String SECRET = "40d33c14-d2e0-4c75-918b-f504dfe72855";
	private static final int ACCESS_TOKEN_TTL = 10 * 60 * 1000;
	private static final int REFRESH_TOKEN_TTL = 30 * 60 * 1000;
	private static final Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());

	public JWTUtils() {
	}

	public static JwtToken createAccessToken(User user, String requestUrl) {
		String accessToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TTL)).withIssuer(requestUrl)
				.withClaim(ROLES,
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		String refreshToken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_TTL)).withIssuer(requestUrl)
				.withClaim(ROLES,
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		return new JwtToken(accessToken, refreshToken);
	}

	public static UsernamePasswordAuthenticationToken validateAccessToken(String accessToken) {
		JWTVerifier jwtVerifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
		String username = decodedJWT.getSubject();
		String[] roles = decodedJWT.getClaim(ROLES).asArray(String.class);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Stream.of(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}

	public static String getUsername(String refreshToken) {
		JWTVerifier jwtVerifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
		return decodedJWT.getSubject();
	}

	public static String getAccessToken(org.college.entity.User user, String url) {
		return JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TTL)).withIssuer(url)
				.withClaim(ROLES, user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
				.sign(algorithm);
	}

}
