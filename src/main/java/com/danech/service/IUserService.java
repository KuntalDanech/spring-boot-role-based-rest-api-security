package com.danech.service;

import java.util.Optional;

import com.danech.model.User;

public interface IUserService {
	public User registerUser(User user);
	Optional<User> findByUserName(String userName);
}
