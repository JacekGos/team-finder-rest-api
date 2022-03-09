package com.jacekg.teamfinder.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jacekg.teamfinder.exceptions.UserNotValidException;
import com.jacekg.teamfinder.role.Role;
import com.jacekg.teamfinder.role.RoleRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl serviceUnderTest;

	@Mock
	private UserRepository userRepository; 

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private ModelMapper modelMapper;
	
	private User user;
	
	private Optional<User> optionalUser;
	
	private UserRequest userRequest;
	
	private UserResponse userResponse;
	
	@BeforeEach
	void setUp() throws Exception {
		
		user = new User(
				10L,
				"username",
				"password",
				"email",
				true, true, true, true,
				Arrays.asList(new Role(1L, "ROLE_USER"), new Role(2L, "ROLE_ADMIN")),
				null,
				null);
		
		optionalUser = Optional.of(user);
		
		userRequest = new UserRequest(
				10L,
				"username",
				"password",
				"password",
				"email"); 
		
		userResponse = new UserResponse(
				10L,
				"username",
				"email",
				"ROLE_USER");
	}

	@Test
	void save_ShouldReturn_UserWithUserRole() {
		
		user.setRoles(Arrays.asList(new Role(1L, "ROLE_USER")));
		
		when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);
		when(modelMapper.map(userRequest, User.class)).thenReturn(user);
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Mockito.any(User.class));
		when(userRepository.save(new User())).thenReturn(user);
		
		UserResponse savedUser = serviceUnderTest.save(userRequest);
		
		verify(userRepository).save(Mockito.any(User.class));
		
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getRole()).isEqualTo("ROLE_USER");
	}
	
	@Test
	void save_WhenUsernameExists_ShouldThrow_UserNotValidException() {
		
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		
		assertThrows(UserNotValidException.class, () -> {
			serviceUnderTest.save(userRequest);
		});
	}
	
	@Test
	void updateRole_ShouldReturn_UserWithAdminRole() {
		
		user.setRoles(Arrays.asList
				(new Role(1L, "ROLE_USER"), new Role(1L, "ROLE_ADMIN")));
		
		userResponse.setRole("ROLE_ADMIN");
		
		when(userRepository.findById(any(Long.class))).thenReturn(optionalUser);
		when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role(1L, "ROLE_USER"));
		when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(new Role(1L, "ROLE_ADMIN"));
		when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);
		
		UserResponse savedUser = serviceUnderTest.updateRole(10L);
		
		verify(userRepository).findById(any(Long.class));
		verify(roleRepository, times(2)).findByName(anyString());
		
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getRole()).isEqualTo("ROLE_ADMIN");
	}
	
	@Test
	void updateRole_WhenUserIdDoesntExists_ShouldThrow_UserNotValidException() {
		
		assertThrows(UserNotValidException.class, () -> {
			serviceUnderTest.updateRole(1L);
		});
	}
	
	@Test
	void getUserData_ShouldReturn_User() {
		
		when(userRepository.findByUsername(anyString())).thenReturn(user);
		when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);
		
		UserResponse savedUser = serviceUnderTest.getUserData(any(Principal.class));
		
		verify(userRepository).findByUsername(anyString());
		
		assertThat(userResponse).isNotNull();
		assertThat(userResponse).hasFieldOrPropertyWithValue("id", 10L);
		assertThat(userResponse).hasFieldOrPropertyWithValue("username", "username");
		assertThat(userResponse).hasFieldOrPropertyWithValue("email", "email");
		assertThat(userResponse).hasFieldOrPropertyWithValue("role", "ROLE_USER");
	}


}
