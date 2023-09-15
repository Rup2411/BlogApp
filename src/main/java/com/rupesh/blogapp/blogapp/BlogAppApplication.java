package com.rupesh.blogapp.blogapp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rupesh.blogapp.blogapp.config.AppConstants;
import com.rupesh.blogapp.blogapp.entities.RoleEntity;
import com.rupesh.blogapp.blogapp.repositories.RoleRepository;




@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{

	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("Rupesh@9038"));
		
		try {
			RoleEntity role = new RoleEntity();
			
			role.setId(AppConstants.ROLE_ADMIN);
			role.setRoleName("ROLE_ADMIN");
			
			RoleEntity role1 = new RoleEntity();
			
			role1.setId(AppConstants.ROLE_USER);
			role1.setRoleName("ROLE_USER");
			
			List<RoleEntity> roleEntities = List.of(role,role1);
			
			List<RoleEntity> result = this.roleRepository.saveAll(roleEntities);
			
			result.forEach(r -> {
				System.out.println(r.getRoleName());
			});
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
