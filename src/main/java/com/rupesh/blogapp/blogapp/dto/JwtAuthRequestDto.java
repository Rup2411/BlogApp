package com.rupesh.blogapp.blogapp.dto;

import lombok.Data;

@Data
public class JwtAuthRequestDto {

	private String username;
	
	private String Password;
}
