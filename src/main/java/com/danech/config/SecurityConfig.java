package com.danech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.danech.filter.SecurityFilter;

/**
 * Most important Component in the Spring Security it will take care of 
 * Authenticate and Authorize requests
 * 
 * 
 * @author dev77
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private InvalidUserAuthEntryPoint entryPoint;
	
	@Autowired
	private SecurityFilter securityFilter;
	
	
	// In case of state less policy we need to provide authentication manager who checks userName and password
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	// Authenticate
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(encoder);
	}
	
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		// We have to disable CSRF
		.csrf().disable()
		
		.authorizeRequests()
		
		// Allow all for registration and login
		.antMatchers("/register","/login")
		.permitAll()

		.antMatchers("/emp/welcome").hasAuthority("EMP")
		// Make sure it is hasRole - ROLE_ prefix will be applied
		// Differences between authority and role is hasRole will applied ROLE prefix in the the authority
		.antMatchers("/emp/products").hasRole("EMP")
		
		.antMatchers("/admin/inventory").hasAuthority("ADMIN")
		.antMatchers("/admin/sales").hasAuthority("ADMIN")
				
		.anyRequest().permitAll()

		//Any Unauthorized user
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(entryPoint)
		
		// Session creation policy would be STATELESS, There is no state
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		// Register the request 2nd request onward
		.and()
		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
		;
	}
}
