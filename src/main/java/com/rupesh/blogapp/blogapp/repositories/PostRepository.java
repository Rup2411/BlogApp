package com.rupesh.blogapp.blogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blogapp.blogapp.entities.CategoryEntity;
import com.rupesh.blogapp.blogapp.entities.PostEntity;
import com.rupesh.blogapp.blogapp.entities.UserEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

	List<PostEntity> findByUserEntity(UserEntity userEntity);
	List<PostEntity> findByCategoryEntity(CategoryEntity categoryEntity);
	
	List<PostEntity> findByPostTitleContaining(String postTitle);
}
