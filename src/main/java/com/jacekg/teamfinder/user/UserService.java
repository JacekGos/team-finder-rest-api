package com.jacekg.teamfinder.user;


public interface UserService {
	
	public UserResponse save(UserRequest user);

	public UserResponse updateRole(Long userId);
}
