package com.example.authentication.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.authentication.model.User;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	private JWTTokenComponent jwtTokenComponent;

	@Autowired
	public JWTRequestFilter(JWTTokenComponent jwtTokenComponent) {
		this.jwtTokenComponent = jwtTokenComponent;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestToken = request.getHeader("Authorization");
		if (requestToken != null && requestToken.startsWith("Bearer ")) {
			String jwtToken = requestToken.substring(7);
			String userName = jwtTokenComponent.getUserNameFromToken(jwtToken);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			if (userName != null && securityContext.getAuthentication() == null) {
				User user = new User();
				user.setUsername(userName);
				user.setRoles(jwtTokenComponent.getRolesFromToken(jwtToken));
				UserDetails userDetails = JWTUserDetailsFactory.create(user);
				if (jwtTokenComponent.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					securityContext.setAuthentication(authenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
