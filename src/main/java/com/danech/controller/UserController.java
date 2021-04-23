package com.danech.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.danech.model.User;
import com.danech.model.UserRequest;
import com.danech.model.UserResponse;
import com.danech.service.IUserService;
import com.danech.util.JwtUtil;

/**
 * 
 * This is public Controller used to login and register the Client/Customer named it as UserController
 * 
 * @author dev77
 *
 */
@RestController
public class UserController {
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("register")
	public ResponseEntity<String> registerUser(@RequestBody User user){
		try {
			user = userService.registerUser(user);
			return ResponseEntity.ok().body("User "+user.getId()+" has been registered");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong please try again leter");
		}
	}
	
	@PostMapping("login")
	public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest){
		// If it is valid then token will be generated otherwise InvalidUserAuthEntrypoint will be triggered.
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userRequest.getUserName(),
						userRequest.getPassword()));

		String token = jwtUtil.generateToken(userRequest.getUserName());
		return ResponseEntity.ok().body(new UserResponse("success",token));
	}
}
