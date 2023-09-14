package com.rupesh.blogapp.blogapp.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Column(name = "first_name", nullable = false, length = 255)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 255)
	private String lastName;
	
	@Column(name = "email", nullable = false, length = 255)
	private String email;
	
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	@Column(name = "about", nullable = false, length = 255)
	private String about;
	
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PostEntity> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
	private List<CommentEntity> comments  = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user", referencedColumnName = "userId"),
	inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> simpleGrantedAuthoroties = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
		
		return simpleGrantedAuthoroties;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
