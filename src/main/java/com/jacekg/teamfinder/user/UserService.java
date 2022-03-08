package com.jacekg.teamfinder.user;

import java.security.Principal;

public interface UserService {
	
	public UserResponse save(UserRequest user);

	public UserResponse updateRole(Long userId);

	public UserResponse getUserData(Principal principal);
}
