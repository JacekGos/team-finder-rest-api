package com.jacekg.teamfinder.user;


public interface UserService {
	
	public UserResponse save(UserRequest user);
	
	public User findByUsername(String username);
	
	public User findByUserId(Long userId);
}
