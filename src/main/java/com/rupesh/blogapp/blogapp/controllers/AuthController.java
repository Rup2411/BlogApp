package com.rupesh.blogapp.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blogapp.blogapp.dto.JwtAuthRequestDto;
import com.rupesh.blogapp.blogapp.dto.JwtAuthResponseDto;
import com.rupesh.blogapp.blogapp.security.JwtTokenHelper;

@RestController
@RequestMapping("/auth/")
public class AuthController {

	@Autowired
	JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
//	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponseDto> createToken(@RequestBody JwtAuthRequestDto request) throws Exception {
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		System.out.println("Generated Token: " + token);
		
		JwtAuthResponseDto responseDto = new JwtAuthResponseDto();
		
		responseDto.setToken(token);
		
		return new ResponseEntity<JwtAuthResponseDto>(responseDto, HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception  {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			
		this.authenticationManager.authenticate(authenticationToken);
		
		}
		catch (BadCredentialsException e){
			
			throw new Exception("Invalid Username or Password");
		}
	}
}
