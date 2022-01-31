package com.jacekg.teamfinder.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jacekg.teamfinder.game.Game;
import com.jacekg.teamfinder.role.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "\"user\"")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "username", nullable = false )
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled;

	@Column(name = "is_non_expired", nullable = false)
	private boolean isNonExpired;

	@Column(name = "is_credentials_non_expired", nullable = false)
	private boolean isCredentialsNonExpired;

	@Column(name = "is_non_locked", nullable = false)
	private boolean isNonLocked;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "organizer",
			cascade = CascadeType.ALL)
	private Game createdGame;
	
	public String getRoleName() {
		
		List<Role> roles = new ArrayList<>(this.roles);
		
		int roleIndex = roles.size() - 1;
		
		return roles.get(roleIndex).getName();
	}
}
