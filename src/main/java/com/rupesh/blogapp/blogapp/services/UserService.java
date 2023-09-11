package com.rupesh.blogapp.blogapp.services;

import java.util.List;

import com.rupesh.blogapp.blogapp.dto.UserDto;

public interface UserService {

	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
}
