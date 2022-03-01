package com.jacekg.teamfinder.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

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
	@Disabled
	void save_ShouldReturn_UserWithAdminRole() {
		
		when(modelMapper.map(user, UserResponse.class)).thenReturn(userResponse);
		when(modelMapper.map(userRequest, User.class)).thenReturn(user);
		when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Mockito.any(User.class));
		when(userRepository.save(new User())).thenReturn(user);
		
		UserResponse savedUser = serviceUnderTest.save(userRequest);
		
		verify(userRepository).save(Mockito.any(User.class));
		
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getRole()).isEqualTo("ROLE_ADMIN");
	}
	
	@Test
	void save_ShouldReturn_UserWithUserRole() {
		
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		
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
	
	@Disabled
	@Test
	void testFindByUsername() {
		fail("Not yet implemented");
	}
	
	@Disabled
	@Test
	void testFindByUserId() {
		fail("Not yet implemented");
	}

}
