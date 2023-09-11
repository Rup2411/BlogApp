package com.rupesh.blogapp.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blogapp.blogapp.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
