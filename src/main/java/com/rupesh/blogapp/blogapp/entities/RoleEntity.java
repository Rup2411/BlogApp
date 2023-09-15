package com.rupesh.blogapp.blogapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RoleEntity {

	@Id
	private int id;
	
	private String roleName;
}
