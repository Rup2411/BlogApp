package com.rupesh.blogapp.blogapp.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rupesh.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.rupesh.blogapp.blogapp.config.AppConstants;
import com.rupesh.blogapp.blogapp.dto.UserDto;
import com.rupesh.blogapp.blogapp.entities.RoleEntity;
import com.rupesh.blogapp.blogapp.entities.UserEntity;
import com.rupesh.blogapp.blogapp.repositories.RoleRepository;
import com.rupesh.blogapp.blogapp.repositories.UserRepository;
import com.rupesh.blogapp.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		UserEntity userEntity = this.dtoToUser(userDto);
		
		userEntity.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		
		RoleEntity roleEntity = this.roleRepository.findById(AppConstants.ROLE_USER).get();
		
		userEntity.getRoles().add(roleEntity);
		
		UserEntity newUser = this.userRepository.saveAndFlush(userEntity);
		
		return this.userToDto(newUser);
	}
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		UserEntity userEntity = this.dtoToUser(userDto);
		
		UserEntity savedUser = this.userRepository.saveAndFlush(userEntity);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		UserEntity userEntity = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("UserEntity", "Id", userId));
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setPassword(userDto.getPassword());
		userEntity.setAbout(userDto.getAbout());
		
		UserEntity updatedUser = this.userRepository.saveAndFlush(userEntity);
		
		UserDto userDto1 = this.userToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		UserEntity userEntity = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("UserEntity", "Id", userId));
		
		return this.userToDto(userEntity);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<UserEntity> userEntities = this.userRepository.findAll();
		
		List<UserDto> userDtos = userEntities.stream().map(userEntity -> this.userToDto(userEntity)).collect(Collectors.toList());
		
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {

		UserEntity userEntity = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("UserEntity", "Id", userId));
		
		this.userRepository.delete(userEntity);

	}
	
	private UserEntity dtoToUser(UserDto userDto) {
		UserEntity userEntity = this.modelMapper.map(userDto, UserEntity.class);
		/*
		userEntity.setId(userDto.getId());
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setPassword(userDto.getPassword());
		userEntity.setAbout(userDto.getAbout());
		*/
		
		return userEntity;
	}
	
	private UserDto userToDto(UserEntity userEntity) {
		
		UserDto userDto = this.modelMapper.map(userEntity, UserDto.class);
		/*
		userDto.setId(userEntity.getId());
		userDto.setFirstName(userEntity.getFirstName());
		userDto.setLastName(userEntity.getLastName());
		userDto.setEmail(userEntity.getEmail());
		userDto.setPassword(userEntity.getPassword());
		userDto.setAbout(userEntity.getAbout());
		*/
		return userDto;
	}

	

}
