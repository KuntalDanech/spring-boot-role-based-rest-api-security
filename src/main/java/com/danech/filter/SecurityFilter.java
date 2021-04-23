package com.danech.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.danech.util.JwtUtil;

// Filter Servlet
@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		// 1. Read the token from the header param
		String token = request.getHeader("Authorization");
		if(token!=null){
			String userName = jwtUtil.getUserName(token);
			
			// If user name !=null && SecurityContext should be null
			if(userName!=null 
					&& SecurityContextHolder
					.getContext().getAuthentication()==null) {
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
				
				// validate token
				boolean isValid = jwtUtil.validateToken(token, userDetails.getUsername());
				if(isValid) {
					UsernamePasswordAuthenticationToken upAuthToken = 
							new UsernamePasswordAuthenticationToken(
									userDetails.getUsername(), 
									userDetails.getPassword(),
									userDetails.getAuthorities());
					upAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// final object storing in Security context authentication details
					SecurityContextHolder.getContext().setAuthentication(upAuthToken);
				}
			}
		}
		//  It will go without security context object if token==null or invlid token
		filterChain.doFilter(request, response);
	}

}
