package com.rupesh.blogapp.blogapp.dto;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private int postId;
	
	@NotNull
	@Size(min = 2, max = 100, message = "Title Must Contain Minimum of 4 Characters")
	private String postTitle;
	
	@NotNull
	@Size(min = 2, max = 3000, message = "Characters Out of Limit For This Post")
	private String postContent;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private List<CommentDto> comments;
}
