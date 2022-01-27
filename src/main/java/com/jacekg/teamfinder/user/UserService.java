package com.jacekg.teamfinder.user;


public interface UserService {
	
	public UserRequest save(UserRequest user);
	
	public User findByUsername(String username);
	
	public User findByUserId(Long userId);
}
