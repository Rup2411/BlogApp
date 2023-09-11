package com.rupesh.blogapp.blogapp.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int userId;
	
	@NotEmpty
	@Size(min = 2, message = "First Name Must Be Greater Than 2")
	private String firstName;
	
	@NotEmpty
	@Size(min = 2, message = "Last Name Must Be Greater Than 2")
	private String lastName;
	
	@Email(message = "Invalid Email")
	private String email;
	
	@NotEmpty
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=.*\\d).{5,15}$", message = "Password must meet the criteria and must contain atleast 1 Upper case Alphabet 1 Lower case Alphabet 1 Special Character and 1 Number"
			+ " " + "Example = @#$%^&+=!")
	private String password;

	@NotNull
	private String about;
	
	private List<CommentDto> comments;
}
