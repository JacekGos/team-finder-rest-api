package com.jacekg.teamfinder.user;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jacekg.teamfinder.role.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	private RoleRepository roleRepository;
	
	private BCryptPasswordEncoder passwordEncoder;
	
	private ModelMapper modelMapper;

	@Override
	public UserRequest save(UserRequest user) {
		return null;
	}

	@Override
	public User findByUsername(String username) {
		return null;
	}

	@Override
	public User findByUserId(Long userId) {
		return null;
	}

}
