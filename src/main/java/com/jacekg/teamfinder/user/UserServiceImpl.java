package com.jacekg.teamfinder.user;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jacekg.teamfinder.exceptions.UserNotValidException;
import com.jacekg.teamfinder.role.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private BCryptPasswordEncoder passwordEncoder;

	private ModelMapper modelMapper;
	
	@PostConstruct
	public void postConstruct() {
		
		modelMapper.addMappings(new PropertyMap<User, UserResponse>() {
			protected void configure() {
				map().setRole(source.getRoleName());
			}
		});
	}
	
	@Transactional
	@Override
	public UserResponse save(UserRequest userRequest) {

		if (userRepository.findByUsername(userRequest.getUsername()) != null) {
			throw new UserNotValidException("Username already exists!");
		}

		userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

		User user = mapUser(userRequest);
		
		return modelMapper.map(userRepository.save(user), UserResponse.class);
	}

	@Override
	public User findByUsername(String username) {
		return null;
	}

	@Override
	public User findByUserId(Long userId) {
		return null;
	}

	private User mapUser(UserRequest userRequest) {

		User user = modelMapper.map(userRequest, User.class);
		user.setEnabled(true);
		user.setNonExpired(true);
		user.setCredentialsNonExpired(true);
		user.setNonLocked(true);
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		
//		if (userRequest.getRole().equals("USER")) {
//			user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
//		} else if (userRequest.getRole().equals("ADMIN")) {
//			user.setRoles(
//					Arrays.asList(roleRepository.findByName("ROLE_USER"), roleRepository.findByName("ROLE_ADMIN")));
//		} else {
//			user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
//		}

		return user;
	}

}
