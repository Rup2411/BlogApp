package com.rupesh.blogapp.blogapp.services;

import com.rupesh.blogapp.blogapp.dto.CommentDto;

public interface CommentService {

	CommentDto createCommentByUser(CommentDto commentDto, Integer postId, Integer userId);
	
	void deleteComment(Integer commentId);
	
}
