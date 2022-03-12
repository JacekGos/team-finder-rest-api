package com.jacekg.teamfinder.user;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jacekg.teamfinder.exceptions.SaveGameException;
import com.jacekg.teamfinder.exceptions.UserNotValidException;
import com.jacekg.teamfinder.game.GameServiceImpl;
import com.jacekg.teamfinder.role.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private BCryptPasswordEncoder passwordEncoder;

	private ModelMapper modelMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
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
		
		logger.info("user " + userRepository.findByUsername(userRequest.getUsername()));
		
		if (userRepository.findByUsername(userRequest.getUsername()) != null) {
			throw new UserNotValidException("Username already exists!");
		}

		userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

		User user = mapUser(userRequest);
		
		return modelMapper.map(userRepository.save(user), UserResponse.class);
	}

	private User mapUser(UserRequest userRequest) {

		User user = modelMapper.map(userRequest, User.class);
		user.setEnabled(true);
		user.setNonExpired(true);
		user.setCredentialsNonExpired(true);
		user.setNonLocked(true);
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

		return user;
	}
	
	@Transactional
	@Override
	public UserResponse updateRole(Long userId) {
		
		Optional<User> foundUser = userRepository.findById(userId);
		
		User user = Optional.ofNullable(foundUser)
				.get()
				.orElseThrow(() -> 
					{throw new UserNotValidException("User with id: " + userId + " doesn't exists");});
		
		logger.info("user2: " + user);
		logger.info("role1: " + user.getRoleName());
		
		user.setRoles(Arrays.asList
				(roleRepository.findByName("ROLE_USER"), roleRepository.findByName("ROLE_ADMIN")));
		
		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	public UserResponse getUserData(Principal principal) {
		
		Optional<User> foundUser = Optional.ofNullable(userRepository.findByUsername(principal.getName()));
		User user = Optional.ofNullable(foundUser)
				.get()
				.orElseThrow(() -> {throw new UserNotValidException("No such user exists");});
		
		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	public User getUserData(String username) {
		
		Optional<User> foundUser = Optional.ofNullable(userRepository.findByUsername(username));
		User user = Optional.ofNullable(foundUser)
				.get()
				.orElseThrow(() -> {throw new UserNotValidException("No such user exists");});
		
		return user;
	}
}
