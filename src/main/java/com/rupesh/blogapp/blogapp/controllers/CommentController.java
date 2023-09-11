package com.rupesh.blogapp.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.blogapp.blogapp.dto.ApiResponse;
import com.rupesh.blogapp.blogapp.dto.CommentDto;
import com.rupesh.blogapp.blogapp.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/post/{postId}/create/comment/user/{userId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId) {
		
		CommentDto createComment = this.commentService.createCommentByUser(commentDto, postId, userId);
		
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
	}
}
