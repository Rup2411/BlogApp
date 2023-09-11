package com.rupesh.blogapp.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

	private int commentId;
	
	@NotBlank
	@Size(min = 2, max = 1000, message = "You cannot write more than 1000 characters")
	private String commentContent;
}
