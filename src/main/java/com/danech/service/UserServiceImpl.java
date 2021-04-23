package com.danech.service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danech.model.User;
import com.danech.repository.UserRepository;

/**
 * Important class for Spring security as It implements UserDetailsService(I) and override
 * loadUserByUsername method and prepare Spring Security User object from the database
 *  
 * 
 * @author dev77
 *
 */
@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Override
	public User registerUser(User user) {
		try {
			user.setPassword(encode.encode(user.getPassword()));
			return userRepository.save(user);			 
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return new User();
		}
	}
	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptl = findByUserName(username);
		if(userOptl.isPresent()) {
			User dbUser = userOptl.get();
			LOGGER.info("USER : "+dbUser);
			return new org.springframework.security.core.userdetails.User(
					dbUser.getUserName(), 
					dbUser.getPassword(), 
					dbUser.getRoles()
					.stream()
					.map(role->new SimpleGrantedAuthority(role))
					.collect(Collectors.toList()));
		}else {
			throw new UsernameNotFoundException("User not found");
		}
	}

}
