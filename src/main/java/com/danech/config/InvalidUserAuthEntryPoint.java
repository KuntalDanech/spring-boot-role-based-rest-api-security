package com.danech.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * The Component is used to give an Unauthorized User response in the rest API.
 * It used HttpServletResponse object and sendError
 * If any Exception is occurred in the httpSecurity it will call this entry point
 *  .exceptionHandling()
 *	.authenticationEntryPoint(entryPoint) 
 * @author dev77
 *
 */
@Component
public class InvalidUserAuthEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(
			HttpServletRequest request, 
			HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {		

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized User");		
		
	}

}
