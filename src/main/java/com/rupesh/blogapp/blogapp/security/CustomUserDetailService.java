package com.rupesh.blogapp.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rupesh.blogapp.blogapp.entities.UserEntity;
import com.rupesh.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.rupesh.blogapp.blogapp.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity = this.userRepository.findByEmail(username)
		.orElseThrow(() -> new ResourceNotFoundException("User", "Email " + username,0));
		
		return userEntity;
	}
}
