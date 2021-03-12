package com.example.authentication.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.authentication.transform.RoleTransform;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenComponent {

	private final String secret;
	private final long tokenLifetime;
	private final RoleTransform roleTransform;

	public JWTTokenComponent(Environment environment) {
		secret = environment.getRequiredProperty("jwt.secret");
		tokenLifetime = Long.parseLong(environment.getRequiredProperty("jwt.lifetime"));
		roleTransform = new RoleTransform();
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private boolean isTokenExpired(String token) {
		Date date = getExpirationFromToken(token);
		return date.before(new Date());
	}

	private String doGenerateToken(UserDetails user) {
		Date now = new Date();
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roleTransform.apply(user.getAuthorities()));
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenLifetime * 1000))
				.addClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public int getRolesFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		int roles = claims.get("roles", Integer.class);
		return roles;
	}

	public String generateToken(UserDetails user) {
		return doGenerateToken(user);
	}

	public boolean validateToken(String token, UserDetails user) {
		String userName = getUserNameFromToken(token);
		return userName.equals(user.getUsername()) && !isTokenExpired(token);
	}
}
