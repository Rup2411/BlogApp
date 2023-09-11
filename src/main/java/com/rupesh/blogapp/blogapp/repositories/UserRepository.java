package com.rupesh.blogapp.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blogapp.blogapp.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
