package com.rupesh.blogapp.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rupesh.blogapp.blogapp.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}
