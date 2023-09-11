package com.rupesh.blogapp.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private int categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Category Must Contain 4 Letters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, max = 1000, message = "You cannot write more than 1000 characters")
	private String categoryDescription;
}
