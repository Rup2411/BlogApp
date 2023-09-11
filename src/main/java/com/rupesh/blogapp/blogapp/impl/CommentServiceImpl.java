package com.rupesh.blogapp.blogapp.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupesh.blogapp.blogapp.dto.CommentDto;
import com.rupesh.blogapp.blogapp.entities.CommentEntity;
import com.rupesh.blogapp.blogapp.entities.PostEntity;
import com.rupesh.blogapp.blogapp.entities.UserEntity;
import com.rupesh.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.rupesh.blogapp.blogapp.repositories.CommentRepository;
import com.rupesh.blogapp.blogapp.repositories.PostRepository;
import com.rupesh.blogapp.blogapp.repositories.UserRepository;
import com.rupesh.blogapp.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CommentDto createCommentByUser(CommentDto commentDto, Integer postId, Integer userId) {
		
		UserEntity userEntity = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		
		PostEntity postEntity = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		CommentEntity commentEntity = this.modelMapper.map(commentDto, CommentEntity.class);
		
		commentEntity.setPostEntity(postEntity);
		commentEntity.setUserEntity(userEntity);
		
		CommentEntity savedComment = this.commentRepository.saveAndFlush(commentEntity);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		CommentEntity commentEntity = this.commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
		
		this.commentRepository.delete(commentEntity);

	}


}
